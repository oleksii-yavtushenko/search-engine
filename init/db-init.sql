CREATE ROLE postgres WITH LOGIN ENCRYPTED PASSWORD '';

CREATE TABLE Pages (
                       ID SERIAL PRIMARY KEY,
                       URL TEXT NOT NULL,
                       Title TEXT NOT NULL,
                       Content TEXT NOT NULL
);

CREATE TABLE Keywords (
                          ID SERIAL PRIMARY KEY,
                          Keyword TEXT NOT NULL
);

CREATE TABLE PageKeywords (
                              PageID INTEGER REFERENCES Pages(ID),
                              KeywordID INTEGER REFERENCES Keywords(ID),
                              PRIMARY KEY (PageID, KeywordID)
);

CREATE TABLE PageRank (
                          PageID INTEGER REFERENCES Pages(ID) PRIMARY KEY,
                          Rank FLOAT NOT NULL
);

CREATE TABLE SearchHistory (
                               ID SERIAL PRIMARY KEY,
                               SearchQuery TEXT NOT NULL,
                               SearchTime TIMESTAMP NOT NULL DEFAULT NOW()
);