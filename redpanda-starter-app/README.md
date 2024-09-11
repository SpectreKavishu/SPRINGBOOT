# Redpanda Starter App

This folder contains a sample application for working with Redpanda. The application consists of three files: `consumer.go`, `producer.go`, and `admin.go`. These files allow you to produce, consume, and create topics on a Redpanda broker.

## Directory Structure

The structure of the folder is as follows:

redpanda-starter-app/
|-- readme.md
|-- admin/
| |-- admin.go
|-- producer/
| |-- producer.go
|-- consumer/
| |-- consumer.go


The password for the application can be found in the app files.

## Usage
To run the application, navigate to the `redpanda-starter-app` directory in your terminal.

```
cd redpanda-starter-app
```

### Creating the Demo Topic

To create the demo topic, execute the following command:

```
go run admin/admin.go
```

### Producing to the Topic

To produce data to the topic, execute the following command:

```
go run producer/producer.go
```

### Consuming the Data

To consume the data from the topic, execute the following command:

```
go run consumer/consumer.go
```
