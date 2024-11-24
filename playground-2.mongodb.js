
const database = 'FootballGamesDatabases';
const collection = 'teams';
const collection2 = "matches"

// The current database to use.
use(database);

// Create a new collection.
db.createCollection(collection);
db.createCollection(collection2);