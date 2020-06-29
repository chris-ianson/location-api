# Location API

This app returns a list of users based on where they live or their current location within a set distance from London. It's built in Scala on top of the Playframework. In order to run it you will need Scala installed. Once installed run the following command to start the service.

```
sbt run
```

Navigate to http://localhost:9000 and you should see two lists of users. You can also access this data in json format using the following endpoints.

## Get list of users living in london

Path     | Supported Methods | Description
-------- | ------ | --------------------------------------------------------------
`/users/city/london` | `GET` | Returns list of users within london

## Get list of users living within a set distance of a city

Path     | Supported Methods | Description
-------- | ------ | --------------------------------------------------------------
`/distance/:miles/:city` | `GET` | Returns list of users within set distance of a city

Parameters           | Type                                                   | Value                         | Description
---------------------|--------------------------------------------------------|------------------------------|-------------------------------------------------------------------------------
miles                | `Int`                                               | N/A  | Distance radius to search within (in miles)
city                | `String`                                               | London  | City to base search on (London is currently only supported city)

## Running the tests

In order to test the app you can run the following command from a terminal

```
sbt test
```