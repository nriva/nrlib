package nrapps.process.samples;

import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.SimpleInitialProperties;
import it.nrsoft.nrlib.process.SortStep;
import it.nrsoft.nrlib.process.Step;
import it.nrsoft.nrlib.process.StepChain;
import it.nrsoft.nrlib.process.StepResult;
import it.nrsoft.nrlib.process.file.FileStep;
import it.nrsoft.nrlib.process.file.FileStepCsv;
import it.nrsoft.nrlib.process.file.FileStepCsvIn;
import it.nrsoft.nrlib.process.file.FileStepCsvOut;
import it.nrsoft.nrlib.process.sql.SqlStep;
import it.nrsoft.nrlib.process.sql.SqlStepDML;
import it.nrsoft.nrlib.process.sql.SqlStepSelect;

public class SampleApp2 {

	public static void main(String[] args) {
		StepChain stepChain = new StepChain("sc1");
		
		InitialProperties initProp = new SimpleInitialProperties();
		
		initProp.addProperty(SqlStep.PROP_SQL_CONNECTION_URL, "jdbc:sqlserver://100GW070\\SVI;databaseName=M3FDBSVI");
		initProp.addProperty(SqlStep.PROP_SQL_CONNECTION_USER, "MDBADM");
		initProp.addProperty(SqlStep.PROP_SQL_CONNECTION_PASSWORD, "M3passw0rd");
		initProp.addProperty(SqlStep.PROP_SQL_DRIVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
		initProp.addProperty(SqlStepSelect.PROP_SQL_SELECT_QUERY, "select * from MVXJDTA.OCUSMA");
		
		Step selectOCUSMA = new SqlStepSelect("dbReader", initProp );
		
		stepChain.addStep(selectOCUSMA);
		
		
		InitialProperties initProp2 = new SimpleInitialProperties();
		initProp2.addProperty(SortStep.PROP_NAME_SORTFIELDS, "OKCUA1,OKCUA2,OKCUA3,OKCUA4");
		Step sort = new SortStep("Sort", initProp2  );

		
		stepChain.addStep(sort);
		
		
		
		InitialProperties initProp3 = new SimpleInitialProperties();
		initProp3.addProperty(FileStep.PROP_FILE_NAME, "c:/temp/ocusma.csv");
		initProp3.addProperty(FileStepCsv.PROP_FILE_CSV_HEADER, "true");
		initProp3.addProperty(FileStepCsv.PROP_FILE_CSV_SEP, ";");
		Step csvOut = new FileStepCsvOut("OutCsv", initProp3  );
		
		
		
		stepChain.addStep(csvOut);

		
		stepChain.run();
		
		
		StepResult result = stepChain.getResult();
		
		System.out.println(result.getMessage());
		

	}

}
