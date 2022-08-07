-- ************************************** users
CREATE TYPE user_type AS ENUM ('COMMON', 'RETAILER');

CREATE TABLE users
(
 id          bigserial NOT NULL,
 external_id uuid NOT NULL,
 full_name   character varying(150) NOT NULL,
 document    character varying(50) NOT NULL,
 email       character varying(50) NOT NULL,
 password    character varying(150) NOT NULL,
 type        user_type NOT NULL,
 balance     decimal NOT NULL,
 created_at  timestamp NOT NULL,
 updated_at  timestamp NOT NULL,
 CONSTRAINT BALANCE_NONNEGATIVE CHECK (balance >= 0),
 CONSTRAINT PK_5 PRIMARY KEY ( id )
);

-- ************************************** transactions
CREATE TABLE transactions
(
 id             bigserial NOT NULL,
 from_user_id   bigint NOT NULL,
 target_user_id bigint NOT NULL,
 value          decimal NOT NULL,
 description    text NOT NULL,
 created_at     timestamp NOT NULL,
 updated_at     timestamp NOT NULL,
 CONSTRAINT PK_17 PRIMARY KEY ( id ),
 CONSTRAINT FK_33 FOREIGN KEY ( from_user_id ) REFERENCES users ( id ),
 CONSTRAINT FK_36 FOREIGN KEY ( target_user_id ) REFERENCES users ( id )
);

CREATE INDEX FK_35 ON transactions
(
 from_user_id
);

CREATE INDEX FK_38 ON transactions
(
 target_user_id
);
