CREATE (colombia:Country {name: 'Colombia'})
CREATE (argentina:Country {name: 'Argentina'})
CREATE (espana:Country {name: 'España'})
CREATE (bogota:City {name: 'Bogotá'})
CREATE (cali:City {name: 'Cali'})
CREATE (medellin:City {name: 'Medellín'})
CREATE (buenos_aires:City {name: 'Buenos Aires'})

CREATE (cien_anos:Book {name: 'Cien años de soledad'})
CREATE (garcia_marquez:Author {name: 'Gabriel garcia marquez'})
CREATE (sudamericana:Editorial {name: 'Editorial Sudamericana'})
//FIRTS WAY RELATIONSHIP FIST USE DATA BASE
CREATE (cien_anos)-[:WRITES]->(garcia_marquez)
CREATE (cien_anos)-[:OWNER]->(sudamericana)
CREATE (sudamericana)-[:IS_FROM]->(buenos_aires)
CREATE (cien_anos)-[:MADE]->(colombia)
//SECOND WAY RELATIONSHIP
MATCH
  (b:Book),
  (a:Author)
  WHERE b.name = 'Cien años de soledad' AND a.name = 'Gabriel garcia marquez'
CREATE (b)-[r:WRITES]->(a)
RETURN type(r);

MATCH
  (b:Book),
  (e:Editorial)
  WHERE b.name = 'Cien años de soledad' AND e.name = 'Editorial Sudamericana'
CREATE (b)-[r:OWNER]->(e)
RETURN type(r);

MATCH
  (e:Editorial),
  (c:City)
  WHERE e.name = 'Editorial Sudamericana' AND c.name = 'Buenos Aires'
CREATE (e)-[r:IS_FROM]->(c)
RETURN type(r);

MATCH
  (b:Book),
  (c:Country)
  WHERE b.name = 'Cien años de soledad' AND c.name = 'Colombia'
CREATE (b)-[r:MADE]->(c)
RETURN type(r);


MATCH(cien_anos:Book {name: 'cien años de soledad'})
SET cien_anos.year = '1967';

MATCH(cien_anos:Book {name: 'cien años de soledad'})
SET cien_anos.pageCount = '471';
////////////////DELETE RELATIONSHIP////////////////////// 

MATCH (b:Book {name: 'Cien años de soledad'})-[r:WRITES]->()
DELETE r

MATCH (b:Book {name: 'Cien años de soledad'})-[r:OWNER]->()
DELETE r

MATCH (b:Book {name: 'Cien años de soledad'})-[r:MADE]->()
DELETE r

MATCH (e:Editorial {name: 'Editorial Sudamericana'})-[r:IS_FROM]->()
DELETE r

// SEARCH RELATIONSHIPS DEPENDS OF DIRECTIONS IT IS IMPORTANT MAKE RELATIONSHIPS TO FILTER THEM NODES

MATCH (c:City {name: 'Buenos Aires'})-[r:IS_FROM]->(e:Editorial {name: 'Lonely Planet'})
RETURN c.name
/////////// DELETE ALL NODES AND RELATIONSHIP/////////////////////////////

MATCH (n)
DETACH DELETE n
