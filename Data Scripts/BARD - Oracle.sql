--
-- ER/Studio Data Architect 9.1 SQL Code Generation
-- Project :      CAP and Data entry.DM1
--
-- Date Created : Tuesday, April 03, 2012 12:45:26
-- Target DBMS : Oracle 11g
--

-- 
-- USER: BARD 
--

CREATE USER BARD IDENTIFIED BY VALUES 'BARD'
;

GRANT CONNECT TO BARD
;
GRANT DBA TO BARD
;

-- 
-- USER: MLBD 
--

CREATE USER MLBD IDENTIFIED BY VALUES 'MLBD'
;

GRANT CONNECT TO MLBD
;
GRANT DBA TO MLBD
;

-- 
-- SEQUENCE: MLBD.Assay_ID 
--

CREATE SEQUENCE MLBD.ASSAY_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.Assay_ID TO BARD
;
GRANT ALTER ON MLBD.Assay_ID TO BARD
;


-- 
-- SEQUENCE: MLBD.Assay_status_ID 
--

CREATE SEQUENCE MLBD.ASSAY_STATUS_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.Assay_status_ID TO BARD
;
GRANT ALTER ON MLBD.Assay_status_ID TO BARD
;


-- 
-- SEQUENCE: MLBD.element_id 
--

CREATE SEQUENCE MLBD.ELEMENT_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.element_id TO BARD
;
GRANT ALTER ON MLBD.element_id TO BARD
;


-- 
-- SEQUENCE: MLBD.element_status_id 
--

CREATE SEQUENCE MLBD.ELEMENT_STATUS_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.element_status_id TO BARD
;
GRANT ALTER ON MLBD.element_status_id TO BARD
;


-- 
-- SEQUENCE: MLBD.experiment_id 
--

CREATE SEQUENCE MLBD.EXPERIMENT_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.experiment_id TO BARD
;
GRANT ALTER ON MLBD.experiment_id TO BARD
;


-- 
-- SEQUENCE: MLBD.experiment_status_id 
--

CREATE SEQUENCE MLBD.EXPERIMENT_STATUS_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.experiment_status_id TO BARD
;
GRANT ALTER ON MLBD.experiment_status_id TO BARD
;


-- 
-- SEQUENCE: MLBD.external_system_id 
--

CREATE SEQUENCE MLBD.EXTERNAL_SYSTEM_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.external_system_id TO BARD
;
GRANT ALTER ON MLBD.external_system_id TO BARD
;


-- 
-- SEQUENCE: MLBD.laboratory_id 
--

CREATE SEQUENCE MLBD.LABORATORY_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.laboratory_id TO BARD
;
GRANT ALTER ON MLBD.laboratory_id TO BARD
;


-- 
-- SEQUENCE: MLBD.measure_context_id 
--

CREATE SEQUENCE MLBD.MEASURE_CONTEXT_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.measure_context_id TO BARD
;
GRANT ALTER ON MLBD.measure_context_id TO BARD
;


-- 
-- SEQUENCE: MLBD.measure_context_item_ID 
--

CREATE SEQUENCE MLBD.MEASURE_CONTEXT_ITEM_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.measure_context_item_ID TO BARD
;
GRANT ALTER ON MLBD.measure_context_item_ID TO BARD
;


-- 
-- SEQUENCE: MLBD.measure_id 
--

CREATE SEQUENCE MLBD.MEASURE_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.measure_id TO BARD
;
GRANT ALTER ON MLBD.measure_id TO BARD
;


-- 
-- SEQUENCE: MLBD.ontology_id 
--

CREATE SEQUENCE MLBD.ONTOLOGY_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.ontology_id TO BARD
;
GRANT ALTER ON MLBD.ontology_id TO BARD
;


-- 
-- SEQUENCE: MLBD.ontology_item_id 
--

CREATE SEQUENCE MLBD.ONTOLOGY_ITEM_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.ontology_item_id TO BARD
;
GRANT ALTER ON MLBD.ontology_item_id TO BARD
;


-- 
-- SEQUENCE: MLBD.project_id 
--

CREATE SEQUENCE MLBD.PROJECT_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.project_id TO BARD
;
GRANT ALTER ON MLBD.project_id TO BARD
;


-- 
-- SEQUENCE: MLBD.protocol_id 
--

CREATE SEQUENCE MLBD.PROTOCOL_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.protocol_id TO BARD
;
GRANT ALTER ON MLBD.protocol_id TO BARD
;


-- 
-- SEQUENCE: MLBD.result_context_ID 
--

CREATE SEQUENCE MLBD.RESULT_CONTEXT_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.result_context_ID TO BARD
;
GRANT ALTER ON MLBD.result_context_ID TO BARD
;


-- 
-- SEQUENCE: MLBD.result_id 
--

CREATE SEQUENCE MLBD.RESULT_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.result_id TO BARD
;
GRANT ALTER ON MLBD.result_id TO BARD
;


-- 
-- SEQUENCE: MLBD.result_status_id 
--

CREATE SEQUENCE MLBD.RESULT_STATUS_ID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT ALTER ON MLBD.result_status_id TO BARD
;
GRANT SELECT ON MLBD.result_status_id TO BARD
;


-- 
-- SEQUENCE: MLBD."result_type_id" 
--

CREATE SEQUENCE MLBD."result_type_id"
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    MAXVALUE 2147483648
    NOCYCLE
    CACHE 2
    NOORDER
;
GRANT SELECT ON MLBD.result_type_id TO BARD
;
GRANT ALTER ON MLBD.result_type_id TO BARD
;


-- 
-- TABLE: MLBD.ASSAY 
--

CREATE TABLE MLBD.ASSAY(
    ASSAY_ID           NUMBER(38, 0)     NOT NULL,
    ASSAY_NAME         VARCHAR2(128)     NOT NULL,
    ASSAY_STATUS_ID    NUMBER(38, 0)     NOT NULL,
    ASSAY_VERSION      VARCHAR2(10)      DEFAULT 1 NOT NULL,
    DESCRIPTION        VARCHAR2(1000),
    DESIGNED_BY        VARCHAR2(100),
    VERSION            NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    DATE_CREATED       TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated       TIMESTAMP(6),
    MODIFIED_BY        VARCHAR2(40),
    CONSTRAINT PK_ASSAY PRIMARY KEY (ASSAY_ID)
)
;



COMMENT ON COLUMN MLBD.ASSAY.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.ASSAY TO BARD
;
GRANT INSERT ON MLBD.ASSAY TO BARD
;
GRANT SELECT ON MLBD.ASSAY TO BARD
;
GRANT UPDATE ON MLBD.ASSAY TO BARD
;

-- 
-- TABLE: MLBD.ASSAY_STATUS 
--

CREATE TABLE MLBD.ASSAY_STATUS(
    ASSAY_STATUS_ID    NUMBER(38, 0)    NOT NULL,
    STATUS             VARCHAR2(20)     NOT NULL,
    VERSION            NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created       TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated       TIMESTAMP(6),
    MODIFIED_BY        VARCHAR2(40),
    CONSTRAINT PK_ASSAY_STATUS PRIMARY KEY (ASSAY_STATUS_ID)
)
;



COMMENT ON COLUMN MLBD.ASSAY_STATUS.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
insert into MLBD.Assay_Status (assay_status_ID, status) values ('1', 'Pending');
insert into MLBD.Assay_Status (assay_status_ID, status) values ('2', 'Active');
insert into MLBD.Assay_Status (assay_status_ID, status) values ('3', 'Superceded');
insert into MLBD.Assay_Status (assay_status_ID) values ('4', 'Retired');
commit;
GRANT DELETE ON MLBD.ASSAY_STATUS TO BARD
;
GRANT INSERT ON MLBD.ASSAY_STATUS TO BARD
;
GRANT SELECT ON MLBD.ASSAY_STATUS TO BARD
;
GRANT UPDATE ON MLBD.ASSAY_STATUS TO BARD
;

-- 
-- TABLE: MLBD.ELEMENT 
--

CREATE TABLE MLBD.ELEMENT(
    ELEMENT_ID           NUMBER(38, 0)     NOT NULL,
    PARENT_ELEMENT_ID    NUMBER(38, 0),
    LABEL                VARCHAR2(128)     NOT NULL,
    DESCRIPTION          VARCHAR2(1000),
    ABBREVIATION         VARCHAR2(20),
    ACRONYM              VARCHAR2(20),
    SYNONYMS             VARCHAR2(1000),
    ELEMENT_STATUS_ID    NUMBER(38, 0)     NOT NULL,
    UNIT                 VARCHAR2(100),
    VERSION              NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created         TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated         TIMESTAMP(6),
    MODIFIED_BY          VARCHAR2(40),
    CONSTRAINT PK_ELEMENT PRIMARY KEY (ELEMENT_ID)
)
;



COMMENT ON COLUMN MLBD.ELEMENT.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.ELEMENT TO BARD
;
GRANT INSERT ON MLBD.ELEMENT TO BARD
;
GRANT SELECT ON MLBD.ELEMENT TO BARD
;
GRANT UPDATE ON MLBD.ELEMENT TO BARD
;

-- 
-- TABLE: MLBD.ELEMENT_STATUS 
--

CREATE TABLE MLBD.ELEMENT_STATUS(
    ELEMENT_STATUS_ID    NUMBER(38, 0)    NOT NULL,
    ELEMENT_STATUS       VARCHAR2(20)     NOT NULL,
    CAPABILITY           VARCHAR2(256),
    VERSION              NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created         TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated         TIMESTAMP(6),
    MODIFIED_BY          VARCHAR2(40),
    CONSTRAINT PK_ELEMENT_STATUS PRIMARY KEY (ELEMENT_STATUS_ID)
)
;



COMMENT ON COLUMN MLBD.ELEMENT_STATUS.CAPABILITY IS 'Description of the actions allowed when elements are in this state'
;
COMMENT ON COLUMN MLBD.ELEMENT_STATUS.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
insert into MLBD.Element_status (Element_status_id, status, Capability) values ('1', 'Pending', 'Element is new, not yet approved but can be used for assasy definition and data entry subject to future curation and approval');
insert into MLBD.Element_status (Element_status_id, status, Capability) values ('2', 'Published', 'Element can be used for any assay definiton or data upload');
insert into MLBD.Element_status (Element_status_id, status, Capability) values ('3', 'Deprecated', 'Element has been replaced by a another one.  It should not be used in new assasy definitions, but can be used when uploading new experiments.  It is subject to future retirement');
insert into MLBD.Element_status (Element_status_id, status, Capability) values ('4', 'Retired', 'Element has been retired and must not be used for new assay definitions.  It can be used for uploading experiment data');
commit;
GRANT DELETE ON MLBD.ELEMENT_STATUS TO BARD
;
GRANT INSERT ON MLBD.ELEMENT_STATUS TO BARD
;
GRANT SELECT ON MLBD.ELEMENT_STATUS TO BARD
;
GRANT UPDATE ON MLBD.ELEMENT_STATUS TO BARD
;

-- 
-- TABLE: MLBD.EXPERIMENT 
--

CREATE TABLE MLBD.EXPERIMENT(
    EXPERIMENT_ID           NUMBER(38, 0)     NOT NULL,
    EXPERIMENT_NAME         VARCHAR2(256)     NOT NULL,
    ASSAY_ID                NUMBER(38, 0)     NOT NULL,
    PROJECT_ID              NUMBER(38, 0),
    EXPERIMENT_STATUS_ID    NUMBER(38, 0)     NOT NULL,
    RUN_DATE_FROM           DATE,
    RUN_DATE_TO             DATE,
    HOLD_UNTIL_DATE         DATE              
                            CONSTRAINT CK_HOLD_UNTIL_DATE CHECK (HOLD_UNTIL_DATE <= sysdate + 366),
    DESCRIPTION             VARCHAR2(1000),
    SOURCE_ID               NUMBER(38, 0)     NOT NULL,
    VERSION                 NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created            TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated            TIMESTAMP(6),
    MODIFIED_BY             VARCHAR2(40),
    CONSTRAINT PK_EXPERIMENT PRIMARY KEY (EXPERIMENT_ID)
)
;



COMMENT ON COLUMN MLBD.EXPERIMENT.HOLD_UNTIL_DATE IS 'can only be set a max of 1 year in the future'
;
COMMENT ON COLUMN MLBD.EXPERIMENT.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.EXPERIMENT TO BARD
;
GRANT INSERT ON MLBD.EXPERIMENT TO BARD
;
GRANT SELECT ON MLBD.EXPERIMENT TO BARD
;
GRANT UPDATE ON MLBD.EXPERIMENT TO BARD
;

-- 
-- TABLE: MLBD.EXPERIMENT_STATUS 
--

CREATE TABLE MLBD.EXPERIMENT_STATUS(
    EXPERIMENT_STATUS_ID    NUMBER(38, 0)     NOT NULL,
    STATUS                  VARCHAR2(20)      NOT NULL,
    CAPABILITY              VARCHAR2(1000),
    VERSION                 NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created            TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated            TIMESTAMP(6),
    MODIFIED_BY             VARCHAR2(40),
    CONSTRAINT PK_EXPERIMENT_STATUS PRIMARY KEY (EXPERIMENT_STATUS_ID)
)
;



COMMENT ON COLUMN MLBD.EXPERIMENT_STATUS.CAPABILITY IS 'describes the actions that can be done with this experiment status and the limitations (this is help text)'
;
COMMENT ON COLUMN MLBD.EXPERIMENT_STATUS.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
insert into MLBD.Experiment_Status (Experiment_status_ID, status, Capability) values ('2', 'Approved', 'Experiment has been approved as ready to upload.  It does not mena results are correct or cannot be changed');
insert into MLBD.Experiment_Status (Experiment_status_ID, status, Capability) values ('3', 'Rejected', 'Experiment data has been rejected as not scientifically valid.  This will not be uploaded to the warehouse');
insert into MLBD.Experiment_Status (Experiment_status_ID, status, Capability) values ('4', 'Held', 'Experiment data is private to the loading institution (Source Laboratory).  The Hold Until Date is set.  Though uploaded it cannot be queried except by the source laboratory');
insert into MLBD.Experiment_Status (Experiment_status_ID, status, Capability) values ('5', 'Uploaded', 'Experiment has been copied into the warehouse and results are available for querying');
insert into MLBD.Experiment_Status (Experiment_status_ID, status, Capability) values ('6', 'Mark for Deletion', 'Experiment has been confirmed as present in the warehouse and may be deleted at any time.');
commit;
GRANT DELETE ON MLBD.EXPERIMENT_STATUS TO BARD
;
GRANT INSERT ON MLBD.EXPERIMENT_STATUS TO BARD
;
GRANT SELECT ON MLBD.EXPERIMENT_STATUS TO BARD
;
GRANT UPDATE ON MLBD.EXPERIMENT_STATUS TO BARD
;

-- 
-- TABLE: MLBD.EXTERNAL_ASSAY 
--

CREATE TABLE MLBD.EXTERNAL_ASSAY(
    EXTERNAL_SYSTEM_ID    NUMBER(38, 0)    NOT NULL,
    ASSAY_ID              NUMBER(38, 0)    NOT NULL,
    EXT_ASSAY_ID          VARCHAR2(128)    NOT NULL,
    VERSION               NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created          TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated          TIMESTAMP(6),
    MODIFIED_BY           VARCHAR2(40),
    CONSTRAINT PK_EXTERNAL_ASSAY PRIMARY KEY (EXTERNAL_SYSTEM_ID, ASSAY_ID)
)
;



COMMENT ON COLUMN MLBD.EXTERNAL_ASSAY.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.EXTERNAL_ASSAY TO BARD
;
GRANT INSERT ON MLBD.EXTERNAL_ASSAY TO BARD
;
GRANT SELECT ON MLBD.EXTERNAL_ASSAY TO BARD
;
GRANT UPDATE ON MLBD.EXTERNAL_ASSAY TO BARD
;

-- 
-- TABLE: MLBD.EXTERNAL_SYSTEM 
--

CREATE TABLE MLBD.EXTERNAL_SYSTEM(
    EXTERNAL_SYSTEM_ID    NUMBER(38, 0)     NOT NULL,
    SYSTEM_NAME           VARCHAR2(128)     NOT NULL,
    OWNER                 VARCHAR2(128),
    SYSTEM_URL            VARCHAR2(1000),
    VERSION               NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created          TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated          TIMESTAMP(6),
    MODIFIED_BY           VARCHAR2(40),
    CONSTRAINT PK_EXTERNAL_SYSTEM PRIMARY KEY (EXTERNAL_SYSTEM_ID)
)
;



COMMENT ON COLUMN MLBD.EXTERNAL_SYSTEM.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.EXTERNAL_SYSTEM TO BARD
;
GRANT INSERT ON MLBD.EXTERNAL_SYSTEM TO BARD
;
GRANT SELECT ON MLBD.EXTERNAL_SYSTEM TO BARD
;
GRANT UPDATE ON MLBD.EXTERNAL_SYSTEM TO BARD
;

-- 
-- TABLE: MLBD.LABORATORY 
--

CREATE TABLE MLBD.LABORATORY(
    LAB_ID          NUMBER(38, 0)     NOT NULL,
    LAB_NAME        VARCHAR2(125)     NOT NULL,
    ABBREVIATION    VARCHAR2(20),
    DESCRIPTION     VARCHAR2(1000),
    LOCATION        VARCHAR2(250),
    VERSION         NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created    TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated    TIMESTAMP(6),
    MODIFIED_BY     VARCHAR2(40),
    CONSTRAINT PK_LABORATORY PRIMARY KEY (LAB_ID)
)
;



COMMENT ON COLUMN MLBD.LABORATORY.LOCATION IS 'Address or other location (website?) for the lab'
;
COMMENT ON COLUMN MLBD.LABORATORY.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.LABORATORY TO BARD
;
GRANT INSERT ON MLBD.LABORATORY TO BARD
;
GRANT SELECT ON MLBD.LABORATORY TO BARD
;
GRANT UPDATE ON MLBD.LABORATORY TO BARD
;

-- 
-- TABLE: MLBD.MEASURE 
--

CREATE TABLE MLBD.MEASURE(
    MEASURE_ID            NUMBER(38, 0)    NOT NULL,
    ASSAY_ID              NUMBER(38, 0)    NOT NULL,
    RESULT_TYPE_ID        NUMBER(38, 0)    NOT NULL,
    ENTRY_UNIT            VARCHAR2(100),
    MEASURE_CONTEXT_ID    NUMBER(38, 0),
    VERSION               NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created          TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated          TIMESTAMP(6),
    MODIFIED_BY           VARCHAR2(40),
    CONSTRAINT PK_MEASURE PRIMARY KEY (MEASURE_ID)
)
;



COMMENT ON COLUMN MLBD.MEASURE.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.MEASURE TO BARD
;
GRANT INSERT ON MLBD.MEASURE TO BARD
;
GRANT SELECT ON MLBD.MEASURE TO BARD
;
GRANT UPDATE ON MLBD.MEASURE TO BARD
;

-- 
-- TABLE: MLBD.MEASURE_CONTEXT 
--

CREATE TABLE MLBD.MEASURE_CONTEXT(
    MEASURE_CONTEXT_ID    NUMBER(38, 0)    NOT NULL,
    CONTEXT_NAME          VARCHAR2(128)    NOT NULL,
    VERSION               NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created          TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated          TIMESTAMP(6),
    MODIFIED_BY           VARCHAR2(40),
    CONSTRAINT PK_MEASURE_CONTEXT PRIMARY KEY (MEASURE_CONTEXT_ID)
)
;



COMMENT ON COLUMN MLBD.MEASURE_CONTEXT.CONTEXT_NAME IS 'default name should be Assay.Name || (select count(*) + 1 from measure_context where assay_ID = assay.assay_ID)'
;
COMMENT ON COLUMN MLBD.MEASURE_CONTEXT.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.MEASURE_CONTEXT TO BARD
;
GRANT INSERT ON MLBD.MEASURE_CONTEXT TO BARD
;
GRANT SELECT ON MLBD.MEASURE_CONTEXT TO BARD
;
GRANT UPDATE ON MLBD.MEASURE_CONTEXT TO BARD
;

-- 
-- TABLE: MLBD.MEASURE_CONTEXT_ITEM 
--

CREATE TABLE MLBD.MEASURE_CONTEXT_ITEM(
    MEASURE_CONTEXT_ITEM_ID    NUMBER(38, 0)    NOT NULL,
    ASSAY_ID                   NUMBER(38, 0)    NOT NULL,
    MEASURE_CONTEXT_ID         NUMBER(38, 0)    NOT NULL,
    GROUP_NO                   NUMBER(10, 0),
    ATTRIBUTE_TYPE             VARCHAR2(20)     NOT NULL
                               CONSTRAINT CK_ATTRIBUTE_TYPE CHECK (ATTRIBUTE_TYPE in ('Fixed', 'List', 'Range', 'Number')),
    ATTRIBUTE_ID               NUMBER(38, 0)    NOT NULL,
    QUALIFIER                  CHAR(2),
    VALUE_ID                   NUMBER(38, 0),
    VALUE_DISPLAY              VARCHAR2(256),
    VALUE_NUM                  BINARY_FLOAT,
    VALUE_MIN                  BINARY_FLOAT,
    VALUE_MAX                  BINARY_FLOAT,
    VERSION                    NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created               TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated               TIMESTAMP(6),
    MODIFIED_BY                VARCHAR2(40),
    CONSTRAINT PK_MEASURE_CONTEXT_ITEM PRIMARY KEY (MEASURE_CONTEXT_ITEM_ID)
)
;



COMMENT ON COLUMN MLBD.MEASURE_CONTEXT_ITEM.ASSAY_ID IS 'This column is useful for creating the assay defintion before the measures and their contexts have been properly grouped.'
;
COMMENT ON COLUMN MLBD.MEASURE_CONTEXT_ITEM.GROUP_NO IS 'rows with the same group_no in the measure context combine to a single group for UI purposes'
;
COMMENT ON COLUMN MLBD.MEASURE_CONTEXT_ITEM.VALUE_DISPLAY IS 'This is not a general text entry field, rather it is an easily displayable text version of the other value columns'
;
COMMENT ON COLUMN MLBD.MEASURE_CONTEXT_ITEM.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.MEASURE_CONTEXT_ITEM TO BARD
;
GRANT INSERT ON MLBD.MEASURE_CONTEXT_ITEM TO BARD
;
GRANT SELECT ON MLBD.MEASURE_CONTEXT_ITEM TO BARD
;
GRANT UPDATE ON MLBD.MEASURE_CONTEXT_ITEM TO BARD
;

-- 
-- TABLE: MLBD.ONTOLOGY 
--

CREATE TABLE MLBD.ONTOLOGY(
    ONTOLOGY_ID      NUMBER(38, 0)     NOT NULL,
    ONTOLOGY_NAME    VARCHAR2(256)     NOT NULL,
    ABBREVIATION     VARCHAR2(20),
    SYSTEM_URL       VARCHAR2(1000),
    VERSION          NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created     TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated     TIMESTAMP(6),
    MODIFIED_BY      VARCHAR2(40),
    CONSTRAINT PK_ONTOLOGY PRIMARY KEY (ONTOLOGY_ID)
)
;



COMMENT ON TABLE MLBD.ONTOLOGY IS 'an external ontology or dictionary or other source of reference data'
;
COMMENT ON COLUMN MLBD.ONTOLOGY.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.ONTOLOGY TO BARD
;
GRANT INSERT ON MLBD.ONTOLOGY TO BARD
;
GRANT SELECT ON MLBD.ONTOLOGY TO BARD
;
GRANT UPDATE ON MLBD.ONTOLOGY TO BARD
;

-- 
-- TABLE: MLBD.ONTOLOGY_ITEM 
--

CREATE TABLE MLBD.ONTOLOGY_ITEM(
    ONTOLOGY_ITEM_ID    NUMBER(38, 0)    NOT NULL,
    ONTOLOGY_ID         NUMBER(38, 0)    NOT NULL,
    ELEMENT_ID          NUMBER(38, 0),
    ITEM_REFERENCE      CHAR(10),
    RESULT_TYPE_ID      NUMBER(38, 0),
    VERSION             NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created        TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated        TIMESTAMP(6),
    MODIFIED_BY         VARCHAR2(40),
    CONSTRAINT PK_ONTOLOGY_ITEM PRIMARY KEY (ONTOLOGY_ITEM_ID)
)
;



COMMENT ON COLUMN MLBD.ONTOLOGY_ITEM.ITEM_REFERENCE IS 'Concatenate this with the Ontology.system_URL for a full URI for the item'
;
COMMENT ON COLUMN MLBD.ONTOLOGY_ITEM.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.ONTOLOGY_ITEM TO BARD
;
GRANT INSERT ON MLBD.ONTOLOGY_ITEM TO BARD
;
GRANT SELECT ON MLBD.ONTOLOGY_ITEM TO BARD
;
GRANT UPDATE ON MLBD.ONTOLOGY_ITEM TO BARD
;

-- 
-- TABLE: MLBD.PROJECT 
--

CREATE TABLE MLBD.PROJECT(
    PROJECT_ID      NUMBER(38, 0)     NOT NULL,
    PROJECT_NAME    VARCHAR2(256)     NOT NULL,
    GROUP_TYPE      VARCHAR2(20)      DEFAULT 'Project' NOT NULL
                    CONSTRAINT CK_PROJECT_TYPE CHECK (GROUP_TYPE in ('Project', 'Campaign', 'Panel', 'Study')),
    DESCRIPTION     VARCHAR2(1000),
    VERSION         NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created    TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated    TIMESTAMP(6),
    MODIFIED_BY     VARCHAR2(40),
    CONSTRAINT PK_PROJECT PRIMARY KEY (PROJECT_ID)
)
;



COMMENT ON COLUMN MLBD.PROJECT.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.PROJECT TO BARD
;
GRANT INSERT ON MLBD.PROJECT TO BARD
;
GRANT SELECT ON MLBD.PROJECT TO BARD
;
GRANT UPDATE ON MLBD.PROJECT TO BARD
;

-- 
-- TABLE: MLBD.PROJECT_ASSAY 
--

CREATE TABLE MLBD.PROJECT_ASSAY(
    ASSAY_ID               NUMBER(38, 0)     NOT NULL,
    PROJECT_ID             NUMBER(38, 0)     NOT NULL,
    STAGE                  VARCHAR2(20)      NOT NULL,
    SEQUENCE_NO            NUMBER(10, 0),
    PROMOTION_THRESHOLD    BINARY_FLOAT,
    PROMOTION_CRITERIA     VARCHAR2(1000),
    VERSION                NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created           TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated           TIMESTAMP(6),
    MODIFIED_BY            VARCHAR2(40),
    CONSTRAINT PK_PROJECT_ASSAY PRIMARY KEY (ASSAY_ID, PROJECT_ID)
)
;



COMMENT ON COLUMN MLBD.PROJECT_ASSAY.SEQUENCE_NO IS 'defines the promotion order (and often the running order) of a set of assays in a project'
;
COMMENT ON COLUMN MLBD.PROJECT_ASSAY.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.PROJECT_ASSAY TO BARD
;
GRANT INSERT ON MLBD.PROJECT_ASSAY TO BARD
;
GRANT SELECT ON MLBD.PROJECT_ASSAY TO BARD
;
GRANT UPDATE ON MLBD.PROJECT_ASSAY TO BARD
;

-- 
-- TABLE: MLBD.PROTOCOL 
--

CREATE TABLE MLBD.PROTOCOL(
    PROTOCOL_ID          NUMBER(38, 0)    NOT NULL,
    PROTOCOL_NAME        VARCHAR2(500)    NOT NULL,
    PROTOCOL_DOCUMENT    LONG RAW,
    ASSAY_ID             NUMBER(38, 0)    NOT NULL,
    VERSION              NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created         TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated         TIMESTAMP(6),
    MODIFIED_BY          VARCHAR2(40),
    CONSTRAINT PK_PROTOCOL PRIMARY KEY (PROTOCOL_ID)
)
;



COMMENT ON COLUMN MLBD.PROTOCOL.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.PROTOCOL TO BARD
;
GRANT INSERT ON MLBD.PROTOCOL TO BARD
;
GRANT SELECT ON MLBD.PROTOCOL TO BARD
;
GRANT UPDATE ON MLBD.PROTOCOL TO BARD
;

-- 
-- TABLE: MLBD.QUALIFIER 
--

CREATE TABLE MLBD.QUALIFIER(
    QUALIFIER       CHAR(2)           NOT NULL,
    DESCRIPTION     VARCHAR2(1000),
    VERSION         NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created    TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated    TIMESTAMP(6),
    MODIFIED_BY     VARCHAR2(40),
    CONSTRAINT PK_QUALIFIER PRIMARY KEY (QUALIFIER)
)
;



COMMENT ON COLUMN MLBD.QUALIFIER.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
insert into mlbd.qualifier values ('=', 'equals');
insert into mlbd.qualifier values ('>=', 'greater than or equal');
insert into mlbd.qualifier values ('<=', 'less than or equal');
insert into mlbd.qualifier values ('<', 'greater than');
insert into mlbd.qualifier values ('>', 'less than');
insert into mlbd.qualifier values ('~', 'approximatley');
insert into mlbd.qualifier values ('>>', 'very much greater than');
insert into mlbd.qualifier values ('<<', 'very much less than');
commit;
-- 
-- TABLE: MLBD.RESULT 
--

CREATE TABLE MLBD.RESULT(
    RESULT_ID            NUMBER(38, 0)    NOT NULL,
    VALUE_DISPLAY        VARCHAR2(256),
    VALUE_NUM            BINARY_FLOAT,
    VALUE_MIN            BINARY_FLOAT,
    VALUE_MAX            BINARY_FLOAT,
    QUALIFIER            CHAR(2),
    RESULT_STATUS_ID     NUMBER(38, 0)    NOT NULL,
    EXPERIMENT_ID        NUMBER(38, 0)    NOT NULL,
    SUBSTANCE_ID         NUMBER(38, 0)    NOT NULL,
    RESULT_CONTEXT_ID    NUMBER(38, 0)    NOT NULL,
    ENTRY_UNIT           VARCHAR2(100),
    RESULT_TYPE_ID       NUMBER(38, 0)    NOT NULL,
    VERSION              NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created         TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated         TIMESTAMP(6),
    MODIFIED_BY          VARCHAR2(40),
    CONSTRAINT PK_RESULT PRIMARY KEY (RESULT_ID)
)
;



COMMENT ON COLUMN MLBD.RESULT.ENTRY_UNIT IS 'This is the units that were used in the UI when uploading the data.  The numerical values are stored in "base_Unit" and NOT in this unit to make future calculaton easier.  This Entry_Unit is recorded to allow accurate re-display of entered results and for data lineage reasons'
;
COMMENT ON COLUMN MLBD.RESULT.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.RESULT TO BARD
;
GRANT INSERT ON MLBD.RESULT TO BARD
;
GRANT SELECT ON MLBD.RESULT TO BARD
;
GRANT UPDATE ON MLBD.RESULT TO BARD
;

-- 
-- TABLE: MLBD.RESULT_CONTEXT 
--

CREATE TABLE MLBD.RESULT_CONTEXT(
    RESULT_CONTEXT_ID    NUMBER(38, 0)    NOT NULL,
    CONTEXT_NAME         VARCHAR2(125),
    VERSION              NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created         TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated         TIMESTAMP(6),
    MODIFIED_BY          VARCHAR2(40),
    CONSTRAINT PK_RESULT_CONTEXT PRIMARY KEY (RESULT_CONTEXT_ID)
)
;



COMMENT ON COLUMN MLBD.RESULT_CONTEXT.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.RESULT_CONTEXT TO BARD
;
GRANT INSERT ON MLBD.RESULT_CONTEXT TO BARD
;
GRANT SELECT ON MLBD.RESULT_CONTEXT TO BARD
;
GRANT UPDATE ON MLBD.RESULT_CONTEXT TO BARD
;

-- 
-- TABLE: MLBD.RESULT_CONTEXT_ITEM 
--

CREATE TABLE MLBD.RESULT_CONTEXT_ITEM(
    RESULT_CONTEXT_ITEM_ID    NUMBER(38, 0)    NOT NULL,
    EXPERIMENT_ID             NUMBER(38, 0)    NOT NULL,
    RESULT_CONTEXT_ID         NUMBER(38, 0)    NOT NULL,
    GROUP_NO                  NUMBER(10, 0),
    ATTRIBUTE_ID              NUMBER(38, 0)    NOT NULL,
    VALUE_ID                  NUMBER(38, 0),
    QUALIFIER                 CHAR(2),
    VALUE_DISPLAY             VARCHAR2(256),
    VALUE_NUM                 BINARY_FLOAT,
    VALUE_MIN                 BINARY_FLOAT,
    VALUE_MAX                 BINARY_FLOAT,
    VERSION                   NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created              TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated              TIMESTAMP(6),
    MODIFIED_BY               VARCHAR2(40),
    CONSTRAINT PK_Result_context_item PRIMARY KEY (RESULT_CONTEXT_ITEM_ID)
)
;



COMMENT ON COLUMN MLBD.RESULT_CONTEXT_ITEM.GROUP_NO IS 'rows with the same group_no in the measure context combine to a single group for UI purposes'
;
COMMENT ON COLUMN MLBD.RESULT_CONTEXT_ITEM.VALUE_DISPLAY IS 'This is not a general text entry field, rather it is an easily displayable text version of the other value columns'
;
COMMENT ON COLUMN MLBD.RESULT_CONTEXT_ITEM.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.RESULT_CONTEXT_ITEM TO BARD
;
GRANT INSERT ON MLBD.RESULT_CONTEXT_ITEM TO BARD
;
GRANT SELECT ON MLBD.RESULT_CONTEXT_ITEM TO BARD
;
GRANT UPDATE ON MLBD.RESULT_CONTEXT_ITEM TO BARD
;

-- 
-- TABLE: MLBD.RESULT_HIERARCHY 
--

CREATE TABLE MLBD.RESULT_HIERARCHY(
    RESULT_ID           NUMBER(38, 0)    NOT NULL,
    PARENT_RESULT_ID    NUMBER(38, 0)    NOT NULL,
    HIERARCHY_TYPE      VARCHAR2(10)     NOT NULL
                        CONSTRAINT CK_RESULT_HIERARCHY_TYPE CHECK (HIERARCHY_TYPE in ('Child', 'Derives')),
    VERSION             NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created        TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated        TIMESTAMP(6),
    MODIFIED_BY         VARCHAR2(40),
    CONSTRAINT PK_RESULT_HIERARCHY PRIMARY KEY (RESULT_ID, PARENT_RESULT_ID)
)
;



COMMENT ON COLUMN MLBD.RESULT_HIERARCHY.HIERARCHY_TYPE IS 'two types of hierarchy are allowed: parent/child where one result is dependant on or grouped with another; derived from where aresult is used to claculate another (e.g. PI used for IC50).  The hierarchy types are mutually exclusive.'
;
COMMENT ON COLUMN MLBD.RESULT_HIERARCHY.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.RESULT_HIERARCHY TO BARD
;
GRANT INSERT ON MLBD.RESULT_HIERARCHY TO BARD
;
GRANT SELECT ON MLBD.RESULT_HIERARCHY TO BARD
;
GRANT UPDATE ON MLBD.RESULT_HIERARCHY TO BARD
;

-- 
-- TABLE: MLBD.RESULT_STATUS 
--

CREATE TABLE MLBD.RESULT_STATUS(
    RESULT_STATUS_ID    NUMBER(38, 0)    NOT NULL,
    STATUS              VARCHAR2(20)     NOT NULL,
    VERSION             NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created        TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated        TIMESTAMP(6),
    MODIFIED_BY         VARCHAR2(40),
    CONSTRAINT PK_RESULT_STATUS PRIMARY KEY (RESULT_STATUS_ID)
)
;



COMMENT ON COLUMN MLBD.RESULT_STATUS.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
insert into MLBD.Result_status (result_status_id, status) values ('1', 'Pending');
insert into MLBD.Result_status (result_status_id, status) values ('2', 'Approved');
insert into MLBD.Result_status (result_status_id, status) values ('3', 'Rejected');
insert into MLBD.Result_status (result_status_id, status) values ('4', 'Uploading');
insert into MLBD.Result_status (result_status_id, status) values ('5', 'Uploaded');
insert into MLBD.Result_status (result_status_id, status) values ('6', 'Mark for Deletion');
commit;
GRANT DELETE ON MLBD.RESULT_STATUS TO BARD
;
GRANT INSERT ON MLBD.RESULT_STATUS TO BARD
;
GRANT SELECT ON MLBD.RESULT_STATUS TO BARD
;
GRANT UPDATE ON MLBD.RESULT_STATUS TO BARD
;

-- 
-- TABLE: MLBD.RESULT_TYPE 
--

CREATE TABLE MLBD.RESULT_TYPE(
    RESULT_TYPE_ID           NUMBER(38, 0)     NOT NULL,
    PARENT_RESULT_TYPE_ID    NUMBER(38, 0),
    RESULT_TYPE_NAME         VARCHAR2(128)     NOT NULL,
    DESCRIPTION              VARCHAR2(1000),
    RESULT_TYPE_STATUS_ID    NUMBER(38, 0)     NOT NULL,
    BASE_UNIT                VARCHAR2(100),
    VERSION                  NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created             TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated             TIMESTAMP(6),
    MODIFIED_BY              VARCHAR2(40),
    CONSTRAINT PK_RESULT_TYPE PRIMARY KEY (RESULT_TYPE_ID)
)
;



COMMENT ON COLUMN MLBD.RESULT_TYPE.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.RESULT_TYPE TO BARD
;
GRANT INSERT ON MLBD.RESULT_TYPE TO BARD
;
GRANT SELECT ON MLBD.RESULT_TYPE TO BARD
;
GRANT UPDATE ON MLBD.RESULT_TYPE TO BARD
;

-- 
-- TABLE: MLBD.STAGE 
--

CREATE TABLE MLBD.STAGE(
    STAGE           VARCHAR2(20)      NOT NULL,
    DESCRIPTION     VARCHAR2(1000),
    VERSION         NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created    TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated    TIMESTAMP(6),
    MODIFIED_BY     VARCHAR2(40),
    CONSTRAINT PK_STAGE PRIMARY KEY (STAGE)
)
;



COMMENT ON COLUMN MLBD.STAGE.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
insert into MLBD.Stage (Stage) values ('Primary');
insert into MLBD.Stage (Stage) values ('Secondary');
insert into MLBD.Stage (Stage) values ('Confirmation');
insert into MLBD.Stage (Stage) values ('Tertiary');
insert into MLBD.Stage (Stage) values ('Counter-screen');
insert into MLBD.Stage (Stage) values ('TBD');
Commit;
GRANT DELETE ON MLBD.STAGE TO BARD
;
GRANT INSERT ON MLBD.STAGE TO BARD
;
GRANT SELECT ON MLBD.STAGE TO BARD
;
GRANT UPDATE ON MLBD.STAGE TO BARD
;

-- 
-- TABLE: MLBD.SUBSTANCE 
--

CREATE TABLE MLBD.SUBSTANCE(
    SUBSTANCE_ID        NUMBER(38, 0)     NOT NULL,
    COMPOUND_ID         NUMBER(10, 0),
    SMILES              VARCHAR2(4000),
    MOLECULAR_WEIGHT    NUMBER(10, 3),
    SUBSTANCE_TYPE      VARCHAR2(20)      NOT NULL
                        CONSTRAINT CK_SUBSTANCE_TYPE CHECK (Substance_Type in ('small molecule', 'protein', 'peptide', 'antibody', 'cell', 'oligonucleotide')),
    VERSION             NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created        TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated        TIMESTAMP(6),
    MODIFIED_BY         VARCHAR2(40),
    CONSTRAINT PK_SUBSTANCE PRIMARY KEY (SUBSTANCE_ID)
)
;



COMMENT ON COLUMN MLBD.SUBSTANCE.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.SUBSTANCE TO BARD
;
GRANT INSERT ON MLBD.SUBSTANCE TO BARD
;
GRANT SELECT ON MLBD.SUBSTANCE TO BARD
;
GRANT UPDATE ON MLBD.SUBSTANCE TO BARD
;

-- 
-- TABLE: MLBD.UNIT 
--

CREATE TABLE MLBD.UNIT(
    UNIT            VARCHAR2(100)     NOT NULL,
    DESCRIPTION     VARCHAR2(1000),
    VERSION         NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created    TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated    TIMESTAMP(6),
    MODIFIED_BY     VARCHAR2(40),
    CONSTRAINT PK_UNIT PRIMARY KEY (UNIT)
)
;



COMMENT ON COLUMN MLBD.UNIT.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.UNIT TO BARD
;
GRANT INSERT ON MLBD.UNIT TO BARD
;
GRANT SELECT ON MLBD.UNIT TO BARD
;
GRANT UPDATE ON MLBD.UNIT TO BARD
;

-- 
-- TABLE: MLBD.UNIT_CONVERSION 
--

CREATE TABLE MLBD.UNIT_CONVERSION(
    FROM_UNIT       VARCHAR2(100)    NOT NULL,
    TO_UNIT         VARCHAR2(100)    NOT NULL,
    MULTIPLIER      BINARY_FLOAT,
    OFFSET          BINARY_FLOAT,
    FORMULA         VARCHAR2(256),
    VERSION         NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created    TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated    TIMESTAMP(6),
    MODIFIED_BY     VARCHAR2(40),
    CONSTRAINT PK_UNIT_CONVERSION PRIMARY KEY (FROM_UNIT, TO_UNIT)
)
;



COMMENT ON COLUMN MLBD.UNIT_CONVERSION.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
GRANT DELETE ON MLBD.UNIT_CONVERSION TO BARD
;
GRANT INSERT ON MLBD.UNIT_CONVERSION TO BARD
;
GRANT SELECT ON MLBD.UNIT_CONVERSION TO BARD
;
GRANT UPDATE ON MLBD.UNIT_CONVERSION TO BARD
;

-- 
-- VIEW: BARD.assay 
--

CREATE VIEW BARD.assay AS
SELECT Ass.ASSAY_ID, Ass.ASSAY_NAME, Ass.ASSAY_STATUS_ID, Ass.ASSAY_VERSION, Ass.DESCRIPTION, Ass.DESIGNED_BY, Ass.VERSION, Ass.Date_Created, Ass.Last_Updated, Ass.MODIFIED_BY
FROM MLBD.ASSAY Ass
;

-- 
-- VIEW: BARD.assay_status 
--

CREATE VIEW BARD.assay_status AS
SELECT Ass.ASSAY_STATUS_ID, Ass.STATUS, Ass.VERSION, Ass.Date_Created, Ass.Last_Updated, Ass.MODIFIED_BY
FROM MLBD.ASSAY_STATUS Ass
;

-- 
-- VIEW: BARD.element 
--

CREATE VIEW BARD.element AS
SELECT El.ELEMENT_ID, El.PARENT_ELEMENT_ID, El.LABEL, El.DESCRIPTION, El.ABBREVIATION, El.ACRONYM, El.SYNONYMS, El.ELEMENT_STATUS_ID, El.UNIT, El.VERSION, El.Date_Created, El.Last_Updated, El.MODIFIED_BY
FROM MLBD.ELEMENT El
;

-- 
-- VIEW: BARD.element_status 
--

CREATE VIEW BARD.element_status AS
SELECT El.ELEMENT_STATUS_ID, El.ELEMENT_STATUS, El.CAPABILITY, El.VERSION, El.Date_Created, El.Last_Updated, El.MODIFIED_BY
FROM MLBD.ELEMENT_STATUS El
;

-- 
-- VIEW: BARD.experiment 
--

CREATE VIEW BARD.experiment AS
SELECT Ex.EXPERIMENT_ID, Ex.EXPERIMENT_NAME, Ex.ASSAY_ID, Ex.PROJECT_ID, Ex.EXPERIMENT_STATUS_ID, Ex.RUN_DATE_FROM, Ex.RUN_DATE_TO, Ex.HOLD_UNTIL_DATE, Ex.DESCRIPTION, Ex.SOURCE_ID, Ex.VERSION, Ex.Date_Created, Ex.Last_Updated, Ex.MODIFIED_BY
FROM MLBD.EXPERIMENT Ex
;

-- 
-- VIEW: BARD.experiment_status 
--

CREATE VIEW BARD.experiment_status AS
SELECT Ex.EXPERIMENT_STATUS_ID, Ex.STATUS, Ex.CAPABILITY, Ex.VERSION, Ex.Date_Created, Ex.Last_Updated, Ex.MODIFIED_BY
FROM MLBD.EXPERIMENT_STATUS Ex
;

-- 
-- VIEW: BARD.external_assay 
--

CREATE VIEW BARD.external_assay AS
SELECT Ex.EXTERNAL_SYSTEM_ID, Ex.ASSAY_ID, Ex.EXT_ASSAY_ID, Ex.VERSION, Ex.Date_Created, Ex.Last_Updated, Ex.MODIFIED_BY
FROM MLBD.EXTERNAL_ASSAY Ex
;

-- 
-- VIEW: BARD.external_system 
--

CREATE VIEW BARD.external_system AS
SELECT Ex.EXTERNAL_SYSTEM_ID, Ex.SYSTEM_NAME, Ex.OWNER, Ex.SYSTEM_URL, Ex.VERSION, Ex.Date_Created, Ex.Last_Updated, Ex.MODIFIED_BY
FROM MLBD.EXTERNAL_SYSTEM Ex
;

-- 
-- VIEW: BARD.laboratory 
--

CREATE VIEW BARD.laboratory AS
SELECT La.LAB_ID, La.LAB_NAME, La.ABBREVIATION, La.DESCRIPTION, La.LOCATION, La.VERSION, La.Date_Created, La.Last_Updated, La.MODIFIED_BY
FROM MLBD.LABORATORY La
;

-- 
-- VIEW: BARD.measure 
--

CREATE VIEW BARD.measure AS
SELECT Me.MEASURE_ID, Me.ASSAY_ID, Me.RESULT_TYPE_ID, Me.ENTRY_UNIT, Me.MEASURE_CONTEXT_ID, Me.VERSION, Me.Date_Created, Me.Last_Updated, Me.MODIFIED_BY
FROM MLBD.MEASURE Me
;

-- 
-- VIEW: BARD.measure_context 
--

CREATE VIEW BARD.measure_context AS
SELECT Me.MEASURE_CONTEXT_ID, Me.CONTEXT_NAME, Me.VERSION, Me.Date_Created, Me.Last_Updated, Me.MODIFIED_BY
FROM MLBD.MEASURE_CONTEXT Me
;

-- 
-- VIEW: BARD.measure_context_item 
--

CREATE VIEW BARD.measure_context_item AS
SELECT Me.MEASURE_CONTEXT_ITEM_ID, Me.ASSAY_ID, Me.MEASURE_CONTEXT_ID, Me.GROUP_NO, Me.ATTRIBUTE_TYPE, Me.ATTRIBUTE_ID, Me.QUALIFIER, Me.VALUE_ID, Me.VALUE_DISPLAY, Me.VALUE_NUM, Me.VALUE_MIN, Me.VALUE_MAX, Me.VERSION, Me.Date_Created, Me.Last_Updated, Me.MODIFIED_BY
FROM MLBD.MEASURE_CONTEXT_ITEM Me
;

-- 
-- VIEW: BARD.ontology 
--

CREATE VIEW BARD.ontology AS
SELECT Ont.ONTOLOGY_ID, Ont.ONTOLOGY_NAME, Ont.ABBREVIATION, Ont.SYSTEM_URL, Ont.VERSION, Ont.Date_Created, Ont.Last_Updated, Ont.MODIFIED_BY
FROM MLBD.ONTOLOGY Ont
;

-- 
-- VIEW: BARD.ontology_item 
--

CREATE VIEW BARD.ontology_item AS
SELECT Ont.ONTOLOGY_ITEM_ID, Ont.ONTOLOGY_ID, Ont.ELEMENT_ID, Ont.ITEM_REFERENCE, Ont.RESULT_TYPE_ID, Ont.VERSION, Ont.Date_Created, Ont.Last_Updated, Ont.MODIFIED_BY
FROM MLBD.ONTOLOGY_ITEM Ont
;

-- 
-- VIEW: BARD.project 
--

CREATE VIEW BARD.project AS
SELECT Pr.PROJECT_ID, Pr.PROJECT_NAME, Pr.GROUP_TYPE, Pr.DESCRIPTION, Pr.VERSION, Pr.Date_Created, Pr.Last_Updated, Pr.MODIFIED_BY
FROM MLBD.PROJECT Pr
;

-- 
-- VIEW: BARD.project_assay 
--

CREATE VIEW BARD.project_assay AS
SELECT Pro.ASSAY_ID, Pro.PROJECT_ID, Pro.STAGE, Pro.SEQUENCE_NO, Pro.PROMOTION_THRESHOLD, Pro.PROMOTION_CRITERIA, Pro.VERSION, Pro.Date_Created, Pro.Last_Updated, Pro.MODIFIED_BY
FROM MLBD.PROJECT_ASSAY Pro
;

-- 
-- VIEW: BARD.protocol 
--

CREATE VIEW BARD.protocol AS
SELECT Pr.PROTOCOL_ID, Pr.PROTOCOL_NAME, Pr.PROTOCOL_DOCUMENT, Pr.ASSAY_ID, Pr.VERSION, Pr.Date_Created, Pr.Last_Updated, Pr.MODIFIED_BY
FROM MLBD.PROTOCOL Pr
;

-- 
-- VIEW: BARD.qualifier 
--

CREATE VIEW BARD.qualifier AS
SELECT Qu.QUALIFIER, Qu.DESCRIPTION, Qu.VERSION, Qu.Date_Created, Qu.Last_Updated, Qu.MODIFIED_BY
FROM MLBD.QUALIFIER Qu
;

-- 
-- VIEW: BARD.result 
--

CREATE VIEW BARD.result AS
SELECT Re.RESULT_ID, Re.VALUE_DISPLAY, Re.VALUE_NUM, Re.VALUE_MIN, Re.VALUE_MAX, Re.QUALIFIER, Re.RESULT_STATUS_ID, Re.EXPERIMENT_ID, Re.SUBSTANCE_ID, Re.RESULT_CONTEXT_ID, Re.ENTRY_UNIT, Re.RESULT_TYPE_ID, Re.VERSION, Re.Date_Created, Re.Last_Updated, Re.MODIFIED_BY
FROM MLBD.RESULT Re
;

-- 
-- VIEW: BARD.result_context 
--

CREATE VIEW BARD.result_context AS
SELECT Re.RESULT_CONTEXT_ID, Re.CONTEXT_NAME, Re.VERSION, Re.Date_Created, Re.Last_Updated, Re.MODIFIED_BY
FROM MLBD.RESULT_CONTEXT Re
;

-- 
-- VIEW: BARD.result_context_item 
--

CREATE VIEW BARD.result_context_item AS
SELECT Re.RESULT_CONTEXT_ITEM_ID, Re.EXPERIMENT_ID, Re.RESULT_CONTEXT_ID, Re.GROUP_NO, Re.ATTRIBUTE_ID, Re.VALUE_ID, Re.QUALIFIER, Re.VALUE_DISPLAY, Re.VALUE_NUM, Re.VALUE_MIN, Re.VALUE_MAX, Re.VERSION, Re.Date_Created, Re.Last_Updated, Re.MODIFIED_BY
FROM MLBD.RESULT_CONTEXT_ITEM Re
;

-- 
-- VIEW: BARD.result_hierarchy 
--

CREATE VIEW BARD.result_hierarchy AS
SELECT Re.RESULT_ID, Re.PARENT_RESULT_ID, Re.HIERARCHY_TYPE, Re.VERSION, Re.Date_Created, Re.Last_Updated, Re.MODIFIED_BY
FROM MLBD.RESULT_HIERARCHY Re
;

-- 
-- VIEW: BARD.result_status 
--

CREATE VIEW BARD.result_status AS
SELECT Re.RESULT_STATUS_ID, Re.STATUS, Re.VERSION, Re.Date_Created, Re.Last_Updated, Re.MODIFIED_BY
FROM MLBD.RESULT_STATUS Re
;

-- 
-- VIEW: BARD.result_type 
--

CREATE VIEW BARD.result_type AS
SELECT Re.RESULT_TYPE_ID, Re.PARENT_RESULT_TYPE_ID, Re.RESULT_TYPE_NAME, Re.DESCRIPTION, Re.RESULT_TYPE_STATUS_ID, Re.BASE_UNIT, Re.VERSION, Re.Date_Created, Re.Last_Updated, Re.MODIFIED_BY
FROM MLBD.RESULT_TYPE Re
WHERE Re.PARENT_RESULT_TYPE_ID = RESULT_TYPE.RESULT_TYPE_ID AND Re.PARENT_RESULT_TYPE_ID = RESULT_TYPE.RESULT_TYPE_ID
;

-- 
-- VIEW: BARD.stage 
--

CREATE VIEW BARD.stage AS
SELECT St.STAGE, St.DESCRIPTION, St.VERSION, St.Date_Created, St.Last_Updated, St.MODIFIED_BY
FROM MLBD.STAGE St
;

-- 
-- VIEW: BARD.substance 
--

CREATE VIEW BARD.substance AS
SELECT Su.SUBSTANCE_ID, Su.COMPOUND_ID, Su.SMILES, Su.MOLECULAR_WEIGHT, Su.SUBSTANCE_TYPE, Su.VERSION, Su.Date_Created, Su.Last_Updated, Su.MODIFIED_BY
FROM MLBD.SUBSTANCE Su
;

-- 
-- VIEW: BARD.unit 
--

CREATE VIEW BARD.unit AS
SELECT Un.UNIT, Un.DESCRIPTION, Un.VERSION, Un.Date_Created, Un.Last_Updated, Un.MODIFIED_BY
FROM MLBD.UNIT Un
;

-- 
-- VIEW: BARD.unit_conversion 
--

CREATE VIEW BARD.unit_conversion AS
SELECT Un.FROM_UNIT, Un.TO_UNIT, Un.MULTIPLIER, Un.OFFSET, Un.FORMULA, Un.VERSION, Un.Date_Created, Un.Last_Updated, Un.MODIFIED_BY
FROM MLBD.UNIT_CONVERSION Un
;

-- 
-- INDEX: MLBD.FK_ASSAY_ASSAY_STATUS_ID 
--

CREATE INDEX MLBD.FK_ASSAY_ASSAY_STATUS_ID ON MLBD.ASSAY(ASSAY_STATUS_ID)
;
-- 
-- INDEX: MLBD.AK_ASSAY_STATUS 
--

CREATE UNIQUE INDEX MLBD.AK_ASSAY_STATUS ON MLBD.ASSAY_STATUS(STATUS)
;
-- 
-- INDEX: MLBD.FK_ELEMENT_ELEMENT_STATUS 
--

CREATE INDEX MLBD.FK_ELEMENT_ELEMENT_STATUS ON MLBD.ELEMENT(ELEMENT_STATUS_ID)
;
-- 
-- INDEX: MLBD.FK_ELEMENT_UNIT 
--

CREATE INDEX MLBD.FK_ELEMENT_UNIT ON MLBD.ELEMENT(UNIT)
;
-- 
-- INDEX: MLBD.FK_ELEMENT_PARENT_ELEMENT 
--

CREATE INDEX MLBD.FK_ELEMENT_PARENT_ELEMENT ON MLBD.ELEMENT(PARENT_ELEMENT_ID)
;
-- 
-- INDEX: MLBD.FK_EXPERIMENT_ASSAY 
--

CREATE INDEX MLBD.FK_EXPERIMENT_ASSAY ON MLBD.EXPERIMENT(ASSAY_ID)
;
-- 
-- INDEX: MLBD.FK_PROJECT_EXPERIMENT 
--

CREATE INDEX MLBD.FK_PROJECT_EXPERIMENT ON MLBD.EXPERIMENT(PROJECT_ID)
;
-- 
-- INDEX: MLBD.FK_EXPERIMENT_EXPRT_STATUS 
--

CREATE INDEX MLBD.FK_EXPERIMENT_EXPRT_STATUS ON MLBD.EXPERIMENT(EXPERIMENT_STATUS_ID)
;
-- 
-- INDEX: MLBD.FK_EXPERIMENT_SOURCE_LAB 
--

CREATE INDEX MLBD.FK_EXPERIMENT_SOURCE_LAB ON MLBD.EXPERIMENT(SOURCE_ID)
;
-- 
-- INDEX: MLBD.FK_EXT_ASSAY_ASSAY 
--

CREATE INDEX MLBD.FK_EXT_ASSAY_ASSAY ON MLBD.EXTERNAL_ASSAY(ASSAY_ID)
;
-- 
-- INDEX: MLBD."FK_EXT_ASSAY_EXT_SYSTEM" 
--

CREATE INDEX MLBD."FK_EXT_ASSAY_EXT_SYSTEM" ON MLBD.EXTERNAL_ASSAY(EXTERNAL_SYSTEM_ID)
;
-- 
-- INDEX: MLBD.FK_MEASURE_ASSAY 
--

CREATE INDEX MLBD.FK_MEASURE_ASSAY ON MLBD.MEASURE(ASSAY_ID)
;
-- 
-- INDEX: MLBD.FK_MEASURE_RESULT_TYPE 
--

CREATE INDEX MLBD.FK_MEASURE_RESULT_TYPE ON MLBD.MEASURE(RESULT_TYPE_ID)
;
-- 
-- INDEX: MLBD.FK_MEASURE_UNIT 
--

CREATE INDEX MLBD.FK_MEASURE_UNIT ON MLBD.MEASURE(ENTRY_UNIT)
;
-- 
-- INDEX: MLBD.FK_MEASURE_M_CONTEXT_ITEM 
--

CREATE INDEX MLBD.FK_MEASURE_M_CONTEXT_ITEM ON MLBD.MEASURE(MEASURE_CONTEXT_ID)
;
-- 
-- INDEX: MLBD.AK_MEASURE_CONTEXT_ITEM 
--

CREATE UNIQUE INDEX MLBD.AK_MEASURE_CONTEXT_ITEM ON MLBD.MEASURE_CONTEXT_ITEM(MEASURE_CONTEXT_ID, GROUP_NO, ATTRIBUTE_ID, VALUE_DISPLAY)
;
-- 
-- INDEX: MLBD.FK_M_CONTEXT_ITEM_M_CONTEXT 
--

CREATE INDEX MLBD.FK_M_CONTEXT_ITEM_M_CONTEXT ON MLBD.MEASURE_CONTEXT_ITEM(MEASURE_CONTEXT_ID)
;
-- 
-- INDEX: MLBD.FK_M_CONTEXT_ITEM_ATTRIBUTE 
--

CREATE INDEX MLBD.FK_M_CONTEXT_ITEM_ATTRIBUTE ON MLBD.MEASURE_CONTEXT_ITEM(ATTRIBUTE_ID)
;
-- 
-- INDEX: MLBD.FK_M_CONTEXT_ITEM_VALUE 
--

CREATE INDEX MLBD.FK_M_CONTEXT_ITEM_VALUE ON MLBD.MEASURE_CONTEXT_ITEM(VALUE_ID)
;
-- 
-- INDEX: MLBD.FK_M_CONTEXT_ITEM_ASSAY 
--

CREATE INDEX MLBD.FK_M_CONTEXT_ITEM_ASSAY ON MLBD.MEASURE_CONTEXT_ITEM(ASSAY_ID)
;
-- 
-- INDEX: MLBD.FK_M_CONTEXT_ITEM_QUALIFIER 
--

CREATE INDEX MLBD.FK_M_CONTEXT_ITEM_QUALIFIER ON MLBD.MEASURE_CONTEXT_ITEM(QUALIFIER)
;
-- 
-- INDEX: MLBD.FK_ONTOLOGY_ITEM_ONTOLOGY 
--

CREATE INDEX MLBD.FK_ONTOLOGY_ITEM_ONTOLOGY ON MLBD.ONTOLOGY_ITEM(ONTOLOGY_ID)
;
-- 
-- INDEX: MLBD.FK_ONTOLOGY_ITEM_ELEMENT 
--

CREATE INDEX MLBD.FK_ONTOLOGY_ITEM_ELEMENT ON MLBD.ONTOLOGY_ITEM(ELEMENT_ID)
;
-- 
-- INDEX: MLBD.FK_ONTOLOGY_ITEM_RESULT_TYPE 
--

CREATE INDEX MLBD.FK_ONTOLOGY_ITEM_RESULT_TYPE ON MLBD.ONTOLOGY_ITEM(RESULT_TYPE_ID)
;
-- 
-- INDEX: MLBD.FK_PROJECT_ASSAY_ASSAY 
--

CREATE INDEX MLBD.FK_PROJECT_ASSAY_ASSAY ON MLBD.PROJECT_ASSAY(ASSAY_ID)
;
-- 
-- INDEX: MLBD.FK_PROJECT_ASSAY_PROJECT 
--

CREATE INDEX MLBD.FK_PROJECT_ASSAY_PROJECT ON MLBD.PROJECT_ASSAY(PROJECT_ID)
;
-- 
-- INDEX: MLBD.FK_PROJECT_ASSAY_STAGE 
--

CREATE INDEX MLBD.FK_PROJECT_ASSAY_STAGE ON MLBD.PROJECT_ASSAY(STAGE)
;
-- 
-- INDEX: MLBD.FK_PROTOCOL_ASSAY 
--

CREATE INDEX MLBD.FK_PROTOCOL_ASSAY ON MLBD.PROTOCOL(ASSAY_ID)
;
-- 
-- INDEX: MLBD.FK_RESULT_RESULT_STATUS 
--

CREATE INDEX MLBD.FK_RESULT_RESULT_STATUS ON MLBD.RESULT(RESULT_STATUS_ID)
;
-- 
-- INDEX: MLBD.FK_RESULT_EXPERIMENT 
--

CREATE INDEX MLBD.FK_RESULT_EXPERIMENT ON MLBD.RESULT(EXPERIMENT_ID)
;
-- 
-- INDEX: MLBD.FK_RESULT_RESULT_CONTEXT 
--

CREATE INDEX MLBD.FK_RESULT_RESULT_CONTEXT ON MLBD.RESULT(RESULT_CONTEXT_ID)
;
-- 
-- INDEX: MLBD.FK_RESULT_SUBSTANCE 
--

CREATE INDEX MLBD.FK_RESULT_SUBSTANCE ON MLBD.RESULT(SUBSTANCE_ID)
;
-- 
-- INDEX: MLBD.FK_RESULT_UNIT 
--

CREATE INDEX MLBD.FK_RESULT_UNIT ON MLBD.RESULT(ENTRY_UNIT)
;
-- 
-- INDEX: MLBD.FK_RESULT_RESULT_TYPE 
--

CREATE INDEX MLBD.FK_RESULT_RESULT_TYPE ON MLBD.RESULT(RESULT_TYPE_ID)
;
-- 
-- INDEX: MLBD.FK_RESULT_QUALIFIER 
--

CREATE INDEX MLBD.FK_RESULT_QUALIFIER ON MLBD.RESULT(QUALIFIER)
;
-- 
-- INDEX: MLBD.FK_R_CONTEXT_ITEM_EXPERIMENT 
--

CREATE INDEX MLBD.FK_R_CONTEXT_ITEM_EXPERIMENT ON MLBD.RESULT_CONTEXT_ITEM(EXPERIMENT_ID)
;
-- 
-- INDEX: MLBD.FK_R_CONTEXT_ITEM_R_CONTEXT 
--

CREATE INDEX MLBD.FK_R_CONTEXT_ITEM_R_CONTEXT ON MLBD.RESULT_CONTEXT_ITEM(RESULT_CONTEXT_ID)
;
-- 
-- INDEX: MLBD.FK_R_CONTEXT_ITEM_ATTRIBUTE 
--

CREATE INDEX MLBD.FK_R_CONTEXT_ITEM_ATTRIBUTE ON MLBD.RESULT_CONTEXT_ITEM(ATTRIBUTE_ID)
;
-- 
-- INDEX: MLBD.FK_R_CONTEXT_ITEM_VALUE 
--

CREATE INDEX MLBD.FK_R_CONTEXT_ITEM_VALUE ON MLBD.RESULT_CONTEXT_ITEM(VALUE_ID)
;
-- 
-- INDEX: MLBD.FK_RESULT_HIERARCHY_RSLT_PRNT 
--

CREATE INDEX MLBD.FK_RESULT_HIERARCHY_RSLT_PRNT ON MLBD.RESULT_HIERARCHY(RESULT_ID)
;
-- 
-- INDEX: MLBD.FK_RESULT_HIERARCHY_RESULT 
--

CREATE INDEX MLBD.FK_RESULT_HIERARCHY_RESULT ON MLBD.RESULT_HIERARCHY(PARENT_RESULT_ID)
;
-- 
-- INDEX: MLBD.FK_RESULT_TYPE_ELEMENT_STATUS 
--

CREATE INDEX MLBD.FK_RESULT_TYPE_ELEMENT_STATUS ON MLBD.RESULT_TYPE(RESULT_TYPE_STATUS_ID)
;
-- 
-- INDEX: MLBD.FK_RESULT_TYPE_UNIT 
--

CREATE INDEX MLBD.FK_RESULT_TYPE_UNIT ON MLBD.RESULT_TYPE(BASE_UNIT)
;
-- 
-- INDEX: MLBD.FK_RESULT_TYPE_RSLT_TYP_PRNT 
--

CREATE INDEX MLBD.FK_RESULT_TYPE_RSLT_TYP_PRNT ON MLBD.RESULT_TYPE(PARENT_RESULT_TYPE_ID)
;
-- 
-- INDEX: MLBD.FK_UNIT_CONVERSION_FROM_UNIT 
--

CREATE INDEX MLBD.FK_UNIT_CONVERSION_FROM_UNIT ON MLBD.UNIT_CONVERSION(FROM_UNIT)
;
-- 
-- INDEX: MLBD.FK_UNIT_CONVERSION_TO_UNIT 
--

CREATE INDEX MLBD.FK_UNIT_CONVERSION_TO_UNIT ON MLBD.UNIT_CONVERSION(TO_UNIT)
;
-- 
-- TABLE: MLBD.ASSAY 
--

ALTER TABLE MLBD.ASSAY ADD CONSTRAINT FK_ASSAY_ASSAY_STATUS_ID 
    FOREIGN KEY (ASSAY_STATUS_ID)
    REFERENCES MLBD.ASSAY_STATUS(ASSAY_STATUS_ID)
;


-- 
-- TABLE: MLBD.ELEMENT 
--

ALTER TABLE MLBD.ELEMENT ADD CONSTRAINT FK_ELEMENT_ELEMENT_STATUS 
    FOREIGN KEY (ELEMENT_STATUS_ID)
    REFERENCES MLBD.ELEMENT_STATUS(ELEMENT_STATUS_ID)
;

ALTER TABLE MLBD.ELEMENT ADD CONSTRAINT FK_ELEMENT_PARENT_ELEMENT 
    FOREIGN KEY (PARENT_ELEMENT_ID)
    REFERENCES MLBD.ELEMENT(ELEMENT_ID)
;

ALTER TABLE MLBD.ELEMENT ADD CONSTRAINT FK_ELEMENT_UNIT 
    FOREIGN KEY (UNIT)
    REFERENCES MLBD.UNIT(UNIT)
;


-- 
-- TABLE: MLBD.EXPERIMENT 
--

ALTER TABLE MLBD.EXPERIMENT ADD CONSTRAINT FK_EXPERIMENT_ASSAY 
    FOREIGN KEY (ASSAY_ID)
    REFERENCES MLBD.ASSAY(ASSAY_ID)
;

ALTER TABLE MLBD.EXPERIMENT ADD CONSTRAINT FK_EXPERIMENT_EXPRT_STATUS 
    FOREIGN KEY (EXPERIMENT_STATUS_ID)
    REFERENCES MLBD.EXPERIMENT_STATUS(EXPERIMENT_STATUS_ID)
;

ALTER TABLE MLBD.EXPERIMENT ADD CONSTRAINT FK_EXPERIMENT_SOURCE_LAB 
    FOREIGN KEY (SOURCE_ID)
    REFERENCES MLBD.LABORATORY(LAB_ID)
;

ALTER TABLE MLBD.EXPERIMENT ADD CONSTRAINT FK_PROJECT_EXPERIMENT 
    FOREIGN KEY (PROJECT_ID)
    REFERENCES MLBD.PROJECT(PROJECT_ID)
;


-- 
-- TABLE: MLBD.EXTERNAL_ASSAY 
--

ALTER TABLE MLBD.EXTERNAL_ASSAY ADD CONSTRAINT FK_EXT_ASSAY_ASSAY 
    FOREIGN KEY (ASSAY_ID)
    REFERENCES MLBD.ASSAY(ASSAY_ID)
;

ALTER TABLE MLBD.EXTERNAL_ASSAY ADD CONSTRAINT FK_EXT_ASSAY_EXT_SYSTEM 
    FOREIGN KEY (EXTERNAL_SYSTEM_ID)
    REFERENCES MLBD.EXTERNAL_SYSTEM(EXTERNAL_SYSTEM_ID)
;


-- 
-- TABLE: MLBD.MEASURE 
--

ALTER TABLE MLBD.MEASURE ADD CONSTRAINT FK_MEASURE_ASSAY 
    FOREIGN KEY (ASSAY_ID)
    REFERENCES MLBD.ASSAY(ASSAY_ID)
;

ALTER TABLE MLBD.MEASURE ADD CONSTRAINT FK_MEASURE_M_CONTEXT_ITEM 
    FOREIGN KEY (MEASURE_CONTEXT_ID)
    REFERENCES MLBD.MEASURE_CONTEXT(MEASURE_CONTEXT_ID)
;

ALTER TABLE MLBD.MEASURE ADD CONSTRAINT FK_MEASURE_RESULT_TYPE 
    FOREIGN KEY (RESULT_TYPE_ID)
    REFERENCES MLBD.RESULT_TYPE(RESULT_TYPE_ID)
;

ALTER TABLE MLBD.MEASURE ADD CONSTRAINT FK_MEASURE_UNIT 
    FOREIGN KEY (ENTRY_UNIT)
    REFERENCES MLBD.UNIT(UNIT)
;


-- 
-- TABLE: MLBD.MEASURE_CONTEXT_ITEM 
--

ALTER TABLE MLBD.MEASURE_CONTEXT_ITEM ADD CONSTRAINT FK_M_CONTEXT_ITEM_ASSAY 
    FOREIGN KEY (ASSAY_ID)
    REFERENCES MLBD.ASSAY(ASSAY_ID)
;

ALTER TABLE MLBD.MEASURE_CONTEXT_ITEM ADD CONSTRAINT FK_M_CONTEXT_ITEM_ATTRIBUTE 
    FOREIGN KEY (ATTRIBUTE_ID)
    REFERENCES MLBD.ELEMENT(ELEMENT_ID)
;

ALTER TABLE MLBD.MEASURE_CONTEXT_ITEM ADD CONSTRAINT FK_M_CONTEXT_ITEM_M_CONTEXT 
    FOREIGN KEY (MEASURE_CONTEXT_ID)
    REFERENCES MLBD.MEASURE_CONTEXT(MEASURE_CONTEXT_ID)
;

ALTER TABLE MLBD.MEASURE_CONTEXT_ITEM ADD CONSTRAINT FK_M_CONTEXT_ITEM_QUALIFIER 
    FOREIGN KEY (QUALIFIER)
    REFERENCES MLBD.QUALIFIER(QUALIFIER)
;

ALTER TABLE MLBD.MEASURE_CONTEXT_ITEM ADD CONSTRAINT FK_M_CONTEXT_ITEM_VALUE 
    FOREIGN KEY (VALUE_ID)
    REFERENCES MLBD.ELEMENT(ELEMENT_ID)
;


-- 
-- TABLE: MLBD.ONTOLOGY_ITEM 
--

ALTER TABLE MLBD.ONTOLOGY_ITEM ADD CONSTRAINT FK_ONTOLOGY_ITEM_ELEMENT 
    FOREIGN KEY (ELEMENT_ID)
    REFERENCES MLBD.ELEMENT(ELEMENT_ID)
;

ALTER TABLE MLBD.ONTOLOGY_ITEM ADD CONSTRAINT FK_ONTOLOGY_ITEM_ONTOLOGY 
    FOREIGN KEY (ONTOLOGY_ID)
    REFERENCES MLBD.ONTOLOGY(ONTOLOGY_ID)
;

ALTER TABLE MLBD.ONTOLOGY_ITEM ADD CONSTRAINT FK_ONTOLOGY_ITEM_RESULT_TYPE 
    FOREIGN KEY (RESULT_TYPE_ID)
    REFERENCES MLBD.RESULT_TYPE(RESULT_TYPE_ID)
;


-- 
-- TABLE: MLBD.PROJECT_ASSAY 
--

ALTER TABLE MLBD.PROJECT_ASSAY ADD CONSTRAINT FK_PROJECT_ASSAY_ASSAY 
    FOREIGN KEY (ASSAY_ID)
    REFERENCES MLBD.ASSAY(ASSAY_ID)
;

ALTER TABLE MLBD.PROJECT_ASSAY ADD CONSTRAINT FK_PROJECT_ASSAY_PROJECT 
    FOREIGN KEY (PROJECT_ID)
    REFERENCES MLBD.PROJECT(PROJECT_ID)
;

ALTER TABLE MLBD.PROJECT_ASSAY ADD CONSTRAINT FK_PROJECT_ASSAY_STAGE 
    FOREIGN KEY (STAGE)
    REFERENCES MLBD.STAGE(STAGE)
;


-- 
-- TABLE: MLBD.PROTOCOL 
--

ALTER TABLE MLBD.PROTOCOL ADD CONSTRAINT FK_PROTOCOL_ASSAY 
    FOREIGN KEY (ASSAY_ID)
    REFERENCES MLBD.ASSAY(ASSAY_ID)
;


-- 
-- TABLE: MLBD.RESULT 
--

ALTER TABLE MLBD.RESULT ADD CONSTRAINT FK_RESULT_EXPERIMENT 
    FOREIGN KEY (EXPERIMENT_ID)
    REFERENCES MLBD.EXPERIMENT(EXPERIMENT_ID)
;

ALTER TABLE MLBD.RESULT ADD CONSTRAINT FK_RESULT_QUALIFIER 
    FOREIGN KEY (QUALIFIER)
    REFERENCES MLBD.QUALIFIER(QUALIFIER)
;

ALTER TABLE MLBD.RESULT ADD CONSTRAINT FK_RESULT_RESULT_CONTEXT 
    FOREIGN KEY (RESULT_CONTEXT_ID)
    REFERENCES MLBD.RESULT_CONTEXT(RESULT_CONTEXT_ID)
;

ALTER TABLE MLBD.RESULT ADD CONSTRAINT FK_RESULT_RESULT_STATUS 
    FOREIGN KEY (RESULT_STATUS_ID)
    REFERENCES MLBD.RESULT_STATUS(RESULT_STATUS_ID)
;

ALTER TABLE MLBD.RESULT ADD CONSTRAINT FK_RESULT_RESULT_TYPE 
    FOREIGN KEY (RESULT_TYPE_ID)
    REFERENCES MLBD.RESULT_TYPE(RESULT_TYPE_ID)
;

ALTER TABLE MLBD.RESULT ADD CONSTRAINT FK_RESULT_SUBSTANCE 
    FOREIGN KEY (SUBSTANCE_ID)
    REFERENCES MLBD.SUBSTANCE(SUBSTANCE_ID)
;

ALTER TABLE MLBD.RESULT ADD CONSTRAINT FK_RESULT_UNIT 
    FOREIGN KEY (ENTRY_UNIT)
    REFERENCES MLBD.UNIT(UNIT)
;


-- 
-- TABLE: MLBD.RESULT_CONTEXT_ITEM 
--

ALTER TABLE MLBD.RESULT_CONTEXT_ITEM ADD CONSTRAINT FK_R_CONTEXT_ITEM_ATTRIBUTE 
    FOREIGN KEY (ATTRIBUTE_ID)
    REFERENCES MLBD.ELEMENT(ELEMENT_ID)
;

ALTER TABLE MLBD.RESULT_CONTEXT_ITEM ADD CONSTRAINT FK_R_CONTEXT_ITEM_EXPERIMENT 
    FOREIGN KEY (EXPERIMENT_ID)
    REFERENCES MLBD.EXPERIMENT(EXPERIMENT_ID)
;

ALTER TABLE MLBD.RESULT_CONTEXT_ITEM ADD CONSTRAINT FK_R_context_item_qualifier 
    FOREIGN KEY (QUALIFIER)
    REFERENCES MLBD.QUALIFIER(QUALIFIER)
;

ALTER TABLE MLBD.RESULT_CONTEXT_ITEM ADD CONSTRAINT FK_R_CONTEXT_ITEM_R_CONTEXT 
    FOREIGN KEY (RESULT_CONTEXT_ID)
    REFERENCES MLBD.RESULT_CONTEXT(RESULT_CONTEXT_ID)
;

ALTER TABLE MLBD.RESULT_CONTEXT_ITEM ADD CONSTRAINT FK_R_CONTEXT_ITEM_VALUE 
    FOREIGN KEY (VALUE_ID)
    REFERENCES MLBD.ELEMENT(ELEMENT_ID)
;


-- 
-- TABLE: MLBD.RESULT_HIERARCHY 
--

ALTER TABLE MLBD.RESULT_HIERARCHY ADD CONSTRAINT FK_RESULT_HIERARCHY_RESULT 
    FOREIGN KEY (PARENT_RESULT_ID)
    REFERENCES MLBD.RESULT(RESULT_ID)
;

ALTER TABLE MLBD.RESULT_HIERARCHY ADD CONSTRAINT FK_RESULT_HIERARCHY_RSLT_PRNT 
    FOREIGN KEY (RESULT_ID)
    REFERENCES MLBD.RESULT(RESULT_ID)
;


-- 
-- TABLE: MLBD.RESULT_TYPE 
--

ALTER TABLE MLBD.RESULT_TYPE ADD CONSTRAINT FK_RESULT_TYPE_ELEMENT_STATUS 
    FOREIGN KEY (RESULT_TYPE_STATUS_ID)
    REFERENCES MLBD.ELEMENT_STATUS(ELEMENT_STATUS_ID)
;

ALTER TABLE MLBD.RESULT_TYPE ADD CONSTRAINT FK_RESULT_TYPE_RSLT_TYP_PRNT 
    FOREIGN KEY (PARENT_RESULT_TYPE_ID)
    REFERENCES MLBD.RESULT_TYPE(RESULT_TYPE_ID)
;

ALTER TABLE MLBD.RESULT_TYPE ADD CONSTRAINT FK_RESULT_TYPE_UNIT 
    FOREIGN KEY (BASE_UNIT)
    REFERENCES MLBD.UNIT(UNIT)
;


-- 
-- TABLE: MLBD.UNIT_CONVERSION 
--

ALTER TABLE MLBD.UNIT_CONVERSION ADD CONSTRAINT FK_UNIT_CONVERSION_FROM_UNIT 
    FOREIGN KEY (FROM_UNIT)
    REFERENCES MLBD.UNIT(UNIT)
;

ALTER TABLE MLBD.UNIT_CONVERSION ADD CONSTRAINT FK_UNIT_CONVERSION_TO_UNIT 
    FOREIGN KEY (TO_UNIT)
    REFERENCES MLBD.UNIT(UNIT)
;


