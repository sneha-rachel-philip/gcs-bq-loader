# gcs-bq-loader

* Sample code to read avro data from cloud storage and load to bigquery, using docker and cloud run
* Avro data included in resources folder - data taken from https://github.com/miguno/avro-cli-examples/blob/master/twitter.avro

### How to Execute
* Clone the code to cloud shell editor
* Make necessary changes such as region, bucket, artifact registry, dataset and table name
* create bucket and artifact registry
* copy avro file to bucket
* create dataset
* In cloud shell navigate to project directory and execute gcloud builds submit --region=REGION
* Replace REGION with the region you have used
* This will create the docker image in artifact registry
* Create cloud run job pointing to the docker image
* Provide the cloud run service account necessary access to gcs
* Start the cloud run job
* The data should be ingested to Bigquery, the table will be created if its not present
