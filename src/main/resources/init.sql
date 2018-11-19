/*
    Database initialization script that runs on every web-application redeployment.
*/

-- Create a database named 'newsmanager'

DROP TABLE IF EXISTS news_labels;
DROP TABLE IF EXISTS labels;
DROP TABLE IF EXISTS news;

CREATE TABLE news (
    id SERIAL PRIMARY KEY,
    title TEXT UNIQUE NOT NULL,
    content TEXT NOT NULL,
    post_date DATE NOT NULL,
	CONSTRAINT title_not_empty CHECK (title <> ''),
	CONSTRAINT content_not_empty CHECK (content <> '')
);

CREATE TABLE labels (
    id SERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL,
	CONSTRAINT name_not_empty CHECK (name <> '')
);

CREATE TABLE news_labels (
    news_id INTEGER NOT NULL,
    label_id INTEGER NOT NULL,
	FOREIGN KEY (news_id) REFERENCES news(id),
	FOREIGN KEY (label_id) REFERENCES labels(id),
	PRIMARY KEY (news_id, label_id)
);

-- Dummy data

INSERT INTO news (title, content, post_date) VALUES
	('News 1', 'Lorem ipsum dolor sit amet, aptent elit, dui vel est sed dictum, eleifend ultrices in sit arcu lectus, tellus nulla lacus ut vestibulum sit ultricies, donec felis sed. Maecenas maecenas non neque vestibulum vel condimentum, in risus feugiat, justo mauris massa accumsan cursus id nibh, ut quam lectus donec, wisi porta ligula porta. Justo sed eleifend massa explicabo phasellus. Et id molestie suspendisse mus, tortor suspendisse nibh purus arcu mauris venenatis, elementum magnis lectus nulla dui tristique mollis, sem mauris suscipit suscipit. Nibh ut natoque sit vestibulum proin nulla, justo ut tellus vel quisque tincidunt nisl, massa ligula. Risus elit libero nullam rhoncus eget in, ut vestibulum, id natoque porta. Luctus vestibulum magna nunc. Laoreet orci sagittis nam in iaculis gravida, morbi consequat orci massa est. Aliquam perspiciatis id ut in dui nulla, eget feugiat tincidunt, ante enim eget nam suscipit, placerat elementum. Vestibulum tristique sit morbi id dignissim condimentum, laoreet fames tortor convallis vel, nonummy tincidunt netus, odio amet vel, amet tristique lorem erat.', '2018-10-07'),
	('News 2', 'Aenean egestas magna viverra et, ante lorem ligula tellus, id metus. Justo dapibus et quisque maecenas dis morbi, euismod proin ac interdum sed ullamcorper consequat. Nec nec quis, vehicula vulputate amet consequatur, rutrum eu hac, sed et ut molestie mauris ut. Consectetuer suscipit varius quisque lectus gravida, quisque fusce nulla sagittis habitasse justo tempus. Ipsum maecenas congue. Lorem sed vivamus.', '2018-10-07'),
	('News 3', 'n neque, ipsum metus. Sapien erat urna, scelerisque id imperdiet proin, phasellus adipiscing, lacus consectetuer quisque porta integer ligula sed. Eget lacus pulvinar lorem. At nibh in id, sociis nec. Orci feugiat in volutpat, facilisis odio commodo a in nunc, quisque vitae quisque, in urna amet. Eget magna luctus faucibus, consequat lorem sapiente amet, fermentum sed nunc vel erat quis a.', '2018-10-08'),
	('News 4', 'Pellentesque dapibus vivamus lobortis interdum libero, orci felis iaculis venenatis diam, fringilla quis eu a lectus. Consequat et purus pulvinar, aliquam a convallis, duis magna pulvinar, nonummy et sed sed pulvinar fusce, fringilla odio velit mauris. Eu a pharetra duis integer, massa suspendisse, lorem tellus sit suspendisse vel eligendi magna, integer sollicitudin orci. Dolor fusce lacus, cursus vitae cum in sit amet, donec risus ac nunc neque velit nibh. Morbi luctus wisi nec massa diam, faucibus luctus sed odio vivamus, mauris pede erat diam, aliquam at neque et tellus nulla duis, fringilla vitae. Sed sit donec id in. Urna interdum magna, mi vitae venenatis, ut varius laoreet erat consequat, nulla vestibulum turpis aenean. Augue leo sociosqu aliquam ullamcorper, nisl placerat lectus amet ad interdum, proin justo wisi.', '2018-10-09');

INSERT INTO labels (name) VALUES
	('today'),
	('cats'),
	('helloworld'),
	('movie'),
	('fun'),
	('code'),
	('java'),
	('php'),
	('starwars'),
	('music');

INSERT INTO news_labels (news_id, label_id) values
    (1, 1),
    (1, 4),
    (1, 6),
    (1, 9),
    (2, 1),
    (2, 2),
    (2, 3),
    (2, 4),
    (3, 1),
    (3, 3),
    (3, 4),
    (3, 5),
    (4, 5),
    (4, 6),
    (4, 7),
    (4, 8),
    (4, 10);
