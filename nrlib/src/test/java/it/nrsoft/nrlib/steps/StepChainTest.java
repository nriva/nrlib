package it.nrsoft.nrlib.steps;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import it.nrsoft.nrlib.process.DataRow;
import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.SimpleDataRow;
import it.nrsoft.nrlib.process.SimpleInitialProperties;
import it.nrsoft.nrlib.process.Step;
import it.nrsoft.nrlib.process.StepChain;
import it.nrsoft.nrlib.process.StepResult;
import it.nrsoft.nrlib.process.file.FileStep;
import it.nrsoft.nrlib.process.file.FileStepCsv;
import it.nrsoft.nrlib.process.file.FileStepCsvOut;
import it.nrsoft.nrlib.process.sql.SqlStep;
import it.nrsoft.nrlib.process.sql.SqlStepSelect;
import it.nrsoft.nrlib.process.transform.GroovyTransformation;
import it.nrsoft.nrlib.process.transform.IdentityTransformation;
import it.nrsoft.nrlib.process.transform.Transformation;
import it.nrsoft.nrlib.process.transform.TransformationStep;
import it.nrsoft.nrlib.script.CachedScriptProvider;
import it.nrsoft.nrlib.script.ScriptProvider;
import it.nrsoft.nrlib.script.groovy.ScriptEngineGroovy;

public class StepChainTest {
	
	@Before
	public void setup() throws SQLException
	{
        Connection connection = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            connection = DriverManager.getConnection("jdbc:derby:firstdb;create=true");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE ACCOUNT(ID int primary key, name VARCHAR(100))");
        statement.execute();
        statement.close();
        
        PreparedStatement statement1 = connection.prepareStatement("INSERT INTO ACCOUNT VALUES(?, ?)");
        statement1.setInt(1, 1);
        statement1.setString(2, "TEST1");
        statement1.execute();
        
        statement1.setInt(1, 2);
        statement1.setString(2, "TEST2");        
        statement1.execute();
        
        statement1.setInt(1, 3);
        statement1.setString(2, "TEST3");        
        statement1.execute();
        
        statement1.close();
        
        
        connection.close();
		
	}
	
	@After
	public void tearDown() throws IOException
	{
		
		try {
			DriverManager.getConnection("jdbc:derby:firstdb;shutdown=true");
		} catch (SQLException e) {
		}
		
		File file = new File("firstdb");
		FileUtils.deleteDirectory(file);
	}
	
	@Test
	public void test() {
		SimpleInitialProperties initProperties1 = new SimpleInitialProperties();
		
		initProperties1.addProperty(FileStep.PROP_FILE_NAME, "c:/temp/pippo.csv");
		initProperties1.addProperty(FileStepCsv.PROP_FILE_CSV_HEADER, "true");
		initProperties1.addProperty(FileStepCsv.PROP_FILE_CSV_SEP, ";");
		
		SimpleInitialProperties initProperties2 = new SimpleInitialProperties();
		initProperties2.addProperty(SqlStep.PROP_SQL_CONNECTION_URL ,"jdbc:derby:firstdb");
		initProperties2.addProperty(SqlStep.PROP_SQL_CONNECTION_USER, "");
		initProperties2.addProperty(SqlStep.PROP_SQL_CONNECTION_PASSWORD, "");
		initProperties2.addProperty(SqlStep.PROP_SQL_DRIVER, "org.apache.derby.jdbc.EmbeddedDriver");
		initProperties2.addProperty(SqlStepSelect.PROP_SQL_SELECT_QUERY, "SELECT * FROM ACCOUNT");
		initProperties2.addProperty(Step.PROP_FIELDMAP, "ID:ID1,NAME:NAME1");
		
		
		
		StepChain chain = new StepChain("sc1");
		
		
		Step step = new SqlStepSelect("sql1", initProperties2);
		chain.addStep(step);
		
		step = new FileStepCsvOut("outCsv1", initProperties1);
		chain.addStep(step);
		
		chain.run();
		
		Assert.assertFalse(chain.getResult().isError());
		
		ProcessData dataOut = chain.getResult().getDataOut();
		
		
		Assert.assertNotNull("dataOut is null", dataOut);
		
		List<DataRow> rows = dataOut.getDataRows();
		
		Assert.assertNotNull(rows);
		
		Assert.assertEquals(3, rows.size());
		
	}
	
	@Test
	public void test2() {
		
		
		
		StepChain chain = new StepChain("sc2");
		Step step = null;
		
		SimpleInitialProperties initProperties1 = new SimpleInitialProperties();
		
		initProperties1.addProperty(SqlStep.PROP_SQL_CONNECTION_URL ,"jdbc:derby:firstdb");
		initProperties1.addProperty(SqlStep.PROP_SQL_CONNECTION_USER, "");
		initProperties1.addProperty(SqlStep.PROP_SQL_CONNECTION_PASSWORD, "");
		initProperties1.addProperty(SqlStep.PROP_SQL_DRIVER, "org.apache.derby.jdbc.EmbeddedDriver");
		initProperties1.addProperty(SqlStepSelect.PROP_SQL_SELECT_QUERY, "SELECT * FROM ACCOUNT");
		initProperties1.addProperty(Step.PROP_FIELDMAP, "ID:ID1,NAME:NAME1");
		
		
		step = new SqlStepSelect("sql1", initProperties1);
		chain.addStep(step);


		SimpleInitialProperties initProperties2 = new SimpleInitialProperties();
		
		TransformationStep _step = new TransformationStep("trans1", initProperties2);
		Transformation transformation1 = new IdentityTransformation("ID1");
		Transformation transformation2 = new IdentityTransformation("NAME1");
		_step.addTransformation("ID2", transformation1 );
		_step.addTransformation("NAME2", transformation2 );
		chain.addStep(_step);

		
		SimpleInitialProperties initProperties3 = new SimpleInitialProperties();
		
		initProperties3.addProperty(FileStep.PROP_FILE_NAME, "c:/temp/pippo.csv");
		initProperties3.addProperty(FileStepCsv.PROP_FILE_CSV_HEADER, "true");
		initProperties3.addProperty(FileStepCsv.PROP_FILE_CSV_SEP, ";");
		
		
		step = new FileStepCsvOut("outCsv1", initProperties3);
		chain.addStep(step);
		
		chain.run();
		
		Assert.assertFalse(chain.getResult().isError());
		
		ProcessData dataOut = chain.getResult().getDataOut();
		
		
		Assert.assertNotNull("dataOut is null", dataOut);
		
		List<DataRow> rows = dataOut.getDataRows();
		
		Assert.assertNotNull(rows);
		
		Assert.assertEquals(3, rows.size());
		
	}
	


}
