# psd-21l
Przetwarzanie strumieni danych i data science - projekt 21L

## Build package

Run: `mvn package`

## Run job command

Run: 

```
./bin/flink run \
      --detached \
      ./jobs/psd-flink/psd-flink-1.0.jar --input jobs/psd-flink/samples.csv
```
