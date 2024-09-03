package org.example;

// [START bigquery_load_table_gcs_avro]
import com.google.cloud.bigquery.*;

// Sample to load Avro data from Cloud Storage into a new BigQuery table
public class App {

    public static void main(String[] args) {
        // TODO(developer): Replace these variables before running the sample.
        String datasetName = "raw_dataset";
        String tableName = "twitter_data";
        String sourceUri = "gs://landing-my-test-project-dev/twitter.avro";
        loadAvroFromGCS(datasetName, tableName, sourceUri);
    }

    public static void loadAvroFromGCS(String datasetName, String tableName, String sourceUri) {
        try {
            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests.
            BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

            TableId tableId = TableId.of(datasetName, tableName);
            Table table = bigquery.getTable(tableId);
            if (table == null){
                try {
                TableDefinition tableDefinition = StandardTableDefinition.of(Schema.of());
                TableInfo tableInfo = TableInfo.newBuilder(tableId, tableDefinition).build();
                bigquery.create(tableInfo);
                    System.out.println("Table created successfully");
                } catch (BigQueryException e) {
                    System.out.println("Table was not created. \n" + e.toString());
                }
            }
            LoadJobConfiguration loadConfig =
                    LoadJobConfiguration.newBuilder(tableId, sourceUri)
                    .setFormatOptions(FormatOptions.avro())
                    // Set the write disposition to overwrite existing table data
                    .setWriteDisposition(JobInfo.WriteDisposition.WRITE_TRUNCATE)
                    .build();

            // Load data from a GCS Avro file into the table
            Job job = bigquery.create(JobInfo.of(loadConfig));
            // Blocks until this load table job completes its execution, either failing or succeeding.
            job = job.waitFor();
            if (job.isDone()) {
                System.out.println("Avro from GCS successfully loaded in a table");
            } else {
                System.out.println(
                        "BigQuery was unable to load into the table due to an error:"
                                + job.getStatus().getError());
            }
        } catch (BigQueryException | InterruptedException e) {
            System.out.println("Column not added during load append \n" + e.toString());
        }
    }
}
// [END bigquery_load_table_gcs_avro]