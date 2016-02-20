# webapp-example

Webapp example - with simple api and front-end

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## Tests

To run tests use the following command:
    
    lein test

## Database Migration

To create db migration, run:

    lein clj-sql-up create <migration-name>

To migrate db, run:

    lein clj-sql-up migrate

To rollback migration, run:

    lein clj-sql-up rollback

## License

Copyright © 2016 FIXME
