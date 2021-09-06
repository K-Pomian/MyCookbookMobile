CREATE TABLE IF NOT EXISTS nutritions(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	calories INTEGER,
	carbs INTEGER,
	proteins INTEGER,
	fat INTEGER,
	dish_name TEXT,
	FOREIGN KEY(dish_name) REFERENCES dishes(name)
	);