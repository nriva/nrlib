package nrapps.process.samples;

import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.SimpleInitialProperties;
import it.nrsoft.nrlib.process.Step;
import it.nrsoft.nrlib.process.StepChain;
import it.nrsoft.nrlib.process.StepResult;
import it.nrsoft.nrlib.process.file.FileStep;
import it.nrsoft.nrlib.process.file.FileStepCsv;
import it.nrsoft.nrlib.process.file.FileStepCsvIn;
import it.nrsoft.nrlib.process.sql.SqlStep;
import it.nrsoft.nrlib.process.sql.SqlStepDML;

public class SampleApp {

	public static void main(String[] args) {
		
		
		
		StepChain stepChain = new StepChain("sc1");
		
		InitialProperties initProp1 = new SimpleInitialProperties();
		
		initProp1.addProperty(FileStepCsv.PROP_FILE_CSV_HEADER, "true");
		initProp1.addProperty(FileStepCsv.PROP_FILE_CSV_SEP, ",");
		initProp1.addProperty(FileStep.PROP_FILE_NAME, "c:/temp/input.csv");
		
		
		Step loadFromCsv = new FileStepCsvIn("csvLoader", initProp1 );
		
		stepChain.addStep(loadFromCsv);
		
		
		InitialProperties initProp2 = new SimpleInitialProperties();
		
		initProp2.addProperty(SqlStep.PROP_SQL_CONNECTION_URL, "jdbc:h2:tcp://localhost/~/test");
		initProp2.addProperty(SqlStep.PROP_SQL_CONNECTION_USER, "sa");
		initProp2.addProperty(SqlStep.PROP_SQL_CONNECTION_PASSWORD, "");
		initProp2.addProperty(SqlStep.PROP_SQL_DRIVER, "org.h2.Driver");
		
		initProp2.addProperty(SqlStepDML.PROP_SQL_DML_TABLE, "COUNTER");
		initProp2.addProperty(SqlStepDML.PROP_SQL_DML_OPERATION, "INSERT");
		
		initProp2.addProperty(SqlStepDML.PROP_SQL_DML_JDBC_CATALOGNAME, "TEST");
		initProp2.addProperty(SqlStepDML.PROP_SQL_DML_JDBC_SCHEMANAME, "PUBLIC");
		
		
		Step insertInDB = new SqlStepDML("dbWriter", initProp2 );
		
		stepChain.addStep(insertInDB);
		
		
		stepChain.run();
		
		
		StepResult result = stepChain.getResult();
		
		System.out.println(result.getMessage());
		

	}

}
