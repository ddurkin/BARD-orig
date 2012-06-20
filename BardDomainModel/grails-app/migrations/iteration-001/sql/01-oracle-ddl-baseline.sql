--
-- ER/Studio Data Architect 9.1 SQL Code Generation
-- Project :      CAP and Data entry.DM1
--
-- Date Created : Thursday, June 14, 2012 10:43:59
-- Target DBMS : Oracle 11g
--

-- DJD generated sequences with following sql, there were some missing in the ddl initially passed on
-- SELECT 'CREATE SEQUENCE ' || SEQUENCE_NAME || 'START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;'
-- FROM USER_SEQUENCES
-- ORDER BY 1
CREATE SEQUENCE ASSAY_DOCUMENT_ID_SEQ START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;
CREATE SEQUENCE ASSAY_ID_SEQ START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;

CREATE SEQUENCE ELEMENT_HIERARCHY_ID_SEQ START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;
CREATE SEQUENCE ELEMENT_ID_SEQ START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;

CREATE SEQUENCE EXPERIMENT_ID_SEQ START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;

CREATE SEQUENCE EXTERNAL_SYSTEM_ID_SEQ START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;
CREATE SEQUENCE MEASURE_CONTEXT_ID_SEQ START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;
CREATE SEQUENCE MEASURE_CONTEXT_ITEM_ID_SEQ START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;
CREATE SEQUENCE MEASURE_ID_SEQ START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;

CREATE SEQUENCE ONTOLOGY_ID_SEQ START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;
CREATE SEQUENCE ONTOLOGY_ITEM_ID_SEQ START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;
CREATE SEQUENCE PROJECT_ASSAY_ID_SEQ START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;
CREATE SEQUENCE PROJECT_ID_SEQ START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;
CREATE SEQUENCE RESULT_CONTEXT_ITEM_ID_SEQ START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;
CREATE SEQUENCE RESULT_ID_SEQ START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;
CREATE SEQUENCE RESULT_TYPE_ID_SEQ START WITH 1 INCREMENT BY 1  MAXVALUE 2147483648 NOCYCLE CACHE 2 NOORDER;

--
-- TABLE: ASSAY
--

CREATE TABLE ASSAY(
    ASSAY_ID                NUMBER(19, 0)     NOT NULL,
    ASSAY_STATUS            VARCHAR2(20)      DEFAULT 'Pending' NOT NULL
                            CONSTRAINT CK_ASSAY_STATUS CHECK (Assay_Status IN ('Pending', 'Active', 'Superceded', 'Retired')),
    ASSAY_NAME              VARCHAR2(1000)    NOT NULL,
    ASSAY_VERSION           VARCHAR2(10)      DEFAULT 1 NOT NULL,
    DESIGNED_BY             VARCHAR2(100),
    READY_FOR_EXTRACTION    VARCHAR2(20)      DEFAULT 'Ready' NOT NULL
                            CONSTRAINT CK_ASSAY_EXTRACTION CHECK (ready_for_extraction IN ('Ready', 'Started', 'Complete')),
    VERSION                 NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    DATE_CREATED            TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated            TIMESTAMP(6),
    MODIFIED_BY             VARCHAR2(40),
    CONSTRAINT PK_ASSAY PRIMARY KEY (ASSAY_ID)
)
;



COMMENT ON COLUMN ASSAY.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: ASSAY_DESCRIPTOR
--

CREATE TABLE ASSAY_DESCRIPTOR(
    NODE_ID           NUMBER(19, 0)     NOT NULL,
    PARENT_NODE_ID    NUMBER(19, 0),
    ELEMENT_ID        NUMBER(19, 0)     NOT NULL,
    ELEMENT_STATUS    VARCHAR2(20)      NOT NULL,
    LABEL             VARCHAR2(128)     NOT NULL,
    DESCRIPTION       VARCHAR2(1000),
    ABBREVIATION      VARCHAR2(20),
    SYNONYMS          VARCHAR2(1000),
    EXTERNAL_URL      VARCHAR2(1000),
    UNIT              VARCHAR2(128),
    CONSTRAINT PK_ASSAY_DESCRIPTOR PRIMARY KEY (NODE_ID)
)
;



--
-- TABLE: Assay_document
--

CREATE TABLE Assay_document(
    Assay_document_ID    NUMBER(19, 0)    NOT NULL,
    ASSAY_ID             NUMBER(19, 0)    NOT NULL,
    Document_Name        VARCHAR2(500)    NOT NULL,
    DOCUMENT_TYPE        VARCHAR2(20)     DEFAULT 'Description' NOT NULL
                         CONSTRAINT CK_ASSAY_DOCUMENT_TYPE CHECK (Document_Type IN ('Description', 'Protocol', 'Comments', 'Paper', 'External URL', 'Other')),
    Document_Content     CLOB             DEFAULT EMPTY_CLOB(),
    VERSION              NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created         TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated         TIMESTAMP(6),
    MODIFIED_BY          VARCHAR2(40),
    CONSTRAINT PK_Assay_document PRIMARY KEY (Assay_document_ID)
)
;



COMMENT ON COLUMN Assay_document.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: BIOLOGY_DESCRIPTOR
--

CREATE TABLE BIOLOGY_DESCRIPTOR(
    NODE_ID           NUMBER(19, 0)     NOT NULL,
    PARENT_NODE_ID    NUMBER(19, 0),
    ELEMENT_ID        NUMBER(19, 0)     NOT NULL,
    ELEMENT_STATUS    VARCHAR2(20)      NOT NULL,
    LABEL             VARCHAR2(128)     NOT NULL,
    DESCRIPTION       VARCHAR2(1000),
    ABBREVIATION      VARCHAR2(20),
    SYNONYMS          VARCHAR2(1000),
    EXTERNAL_URL      VARCHAR2(1000),
    UNIT              VARCHAR2(128),
    CONSTRAINT PK_BIOLOGY_DESCRIPTOR PRIMARY KEY (NODE_ID)
)
;

--
-- TABLE: ELEMENT
--

CREATE TABLE ELEMENT(
    ELEMENT_ID              NUMBER(19, 0)     NOT NULL,
    ELEMENT_STATUS          VARCHAR2(20)      DEFAULT 'Pending' NOT NULL
                            CONSTRAINT CK_ELEMENT_STATUS CHECK (Element_Status IN ('Pending', 'Published', 'Deprecated', 'Retired')),
    LABEL                   VARCHAR2(128)     NOT NULL,
    DESCRIPTION             VARCHAR2(1000),
    ABBREVIATION            VARCHAR2(20),
    SYNONYMS                VARCHAR2(1000),
    UNIT                    VARCHAR2(128),
    EXTERNAL_URL            VARCHAR2(1000),
    READY_FOR_EXTRACTION    VARCHAR2(20)      DEFAULT 'Ready' NOT NULL
                            CONSTRAINT CK_ELEMENT_EXTRACTION CHECK (ready_for_extraction IN ('Ready', 'Started', 'Complete')),
    VERSION                 NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created            TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated            TIMESTAMP(6),
    MODIFIED_BY             VARCHAR2(40),
    CONSTRAINT PK_ELEMENT PRIMARY KEY (ELEMENT_ID)
)
;



COMMENT ON COLUMN ELEMENT.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: ELEMENT_HIERARCHY
--

CREATE TABLE ELEMENT_HIERARCHY(
    ELEMENT_HIERARCHY_ID    NUMBER(19, 0)    NOT NULL,
    PARENT_ELEMENT_ID       NUMBER(19, 0),
    CHILD_ELEMENT_ID        NUMBER(19, 0)    NOT NULL,
    RELATIONSHIP_TYPE       VARCHAR2(40)     NOT NULL,
    VERSION                 NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    DATE_CREATED            TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    LAST_UPDATED            TIMESTAMP(6),
    MODIFIED_BY             VARCHAR2(40),
    CONSTRAINT PK_ELEMENT_HIERARCHY PRIMARY KEY (ELEMENT_HIERARCHY_ID)
)
;



COMMENT ON COLUMN ELEMENT_HIERARCHY.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: EXPERIMENT
--

CREATE TABLE EXPERIMENT(
    EXPERIMENT_ID           NUMBER(19, 0)     NOT NULL,
    EXPERIMENT_NAME         VARCHAR2(1000)     NOT NULL,
    EXPERIMENT_STATUS       VARCHAR2(20)      DEFAULT 'Pending' NOT NULL
                            CONSTRAINT CK_EXPERIMENT_STATUS CHECK (Experiment_Status IN ('Pending', 'Approved', 'Rejected', 'Revised')),
    READY_FOR_EXTRACTION    VARCHAR2(20)      DEFAULT 'Ready' NOT NULL
                            CONSTRAINT CK_EXPERIMENT_EXTRACTION CHECK (ready_for_extraction IN ('Ready', 'Started', 'Complete')),
    ASSAY_ID                NUMBER(19, 0)     NOT NULL,
    PROJECT_ID              NUMBER(19, 0),
    LABORATORY_ID           NUMBER(19, 0),
    RUN_DATE_FROM           DATE,
    RUN_DATE_TO             DATE,
    HOLD_UNTIL_DATE         DATE,
    DESCRIPTION             VARCHAR2(1000),
    VERSION                 NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created            TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated            TIMESTAMP(6),
    MODIFIED_BY             VARCHAR2(40),
    CONSTRAINT PK_EXPERIMENT PRIMARY KEY (EXPERIMENT_ID)
)
;



COMMENT ON COLUMN EXPERIMENT.HOLD_UNTIL_DATE IS 'can only be set a max of 1 year in the future'
;
COMMENT ON COLUMN EXPERIMENT.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: EXTERNAL_ASSAY
--

CREATE TABLE EXTERNAL_ASSAY(
    EXTERNAL_SYSTEM_ID    NUMBER(19, 0)    NOT NULL,
    ASSAY_ID              NUMBER(19, 0)    NOT NULL,
    EXT_ASSAY_ID          VARCHAR2(128)    NOT NULL,
    VERSION               NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created          TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated          TIMESTAMP(6),
    MODIFIED_BY           VARCHAR2(40),
    CONSTRAINT PK_EXTERNAL_ASSAY PRIMARY KEY (EXTERNAL_SYSTEM_ID, ASSAY_ID)
)
;



COMMENT ON COLUMN EXTERNAL_ASSAY.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: EXTERNAL_SYSTEM
--

CREATE TABLE EXTERNAL_SYSTEM(
    EXTERNAL_SYSTEM_ID    NUMBER(19, 0)     NOT NULL,
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



COMMENT ON COLUMN EXTERNAL_SYSTEM.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: INSTANCE_DESCRIPTOR
--

CREATE TABLE INSTANCE_DESCRIPTOR(
    NODE_ID           NUMBER(19, 0)     NOT NULL,
    PARENT_NODE_ID    NUMBER(19, 0),
    ELEMENT_ID        NUMBER(19, 0)     NOT NULL,
    ELEMENT_STATUS    VARCHAR2(20)      NOT NULL,
    LABEL             VARCHAR2(128)     NOT NULL,
    DESCRIPTION       VARCHAR2(1000),
    ABBREVIATION      VARCHAR2(20),
    SYNONYMS          VARCHAR2(1000),
    EXTERNAL_URL      VARCHAR2(1000),
    UNIT              VARCHAR2(128),
    CONSTRAINT PK_INSTANCE_DESCRIPTOR PRIMARY KEY (NODE_ID)
)
;



--
-- TABLE: LABORATORY
--

CREATE TABLE LABORATORY(
    NODE_ID              NUMBER(19, 0)     NOT NULL,
    PARENT_NODE_ID       NUMBER(19, 0),
    LABORATORY_ID        NUMBER(19, 0)     NOT NULL,
    LABORATORY_STATUS    VARCHAR2(20)      NOT NULL,
    LABORATORY           VARCHAR2(128)     NOT NULL,
    DESCRIPTION          VARCHAR2(1000),
    CONSTRAINT PK_LABORATORY PRIMARY KEY (NODE_ID)
)
;



--
-- TABLE: MEASURE
--

CREATE TABLE MEASURE(
    MEASURE_ID            NUMBER(19, 0)    NOT NULL,
    ASSAY_ID              NUMBER(19, 0)    NOT NULL,
    MEASURE_CONTEXT_ID    NUMBER(19, 0)    NOT NULL,
    PARENT_MEASURE_ID     NUMBER(19, 0),
    RESULT_TYPE_ID        NUMBER(19, 0)    NOT NULL,
    ENTRY_UNIT            VARCHAR2(128),
    VERSION               NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created          TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated          TIMESTAMP(6),
    MODIFIED_BY           VARCHAR2(40),
    CONSTRAINT PK_MEASURE PRIMARY KEY (MEASURE_ID)
)
;



COMMENT ON COLUMN MEASURE.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: MEASURE_CONTEXT
--

CREATE TABLE MEASURE_CONTEXT(
    MEASURE_CONTEXT_ID    NUMBER(19, 0)    NOT NULL,
    ASSAY_ID              NUMBER(19, 0)    NOT NULL,
    CONTEXT_NAME          VARCHAR2(128)    NOT NULL,
    VERSION               NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created          TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated          TIMESTAMP(6),
    MODIFIED_BY           VARCHAR2(40),
    CONSTRAINT PK_MEASURE_CONTEXT PRIMARY KEY (MEASURE_CONTEXT_ID)
)
;



COMMENT ON COLUMN MEASURE_CONTEXT.CONTEXT_NAME IS 'default name should be ''Context for '' || Result_Type_Name'
;
COMMENT ON COLUMN MEASURE_CONTEXT.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: MEASURE_CONTEXT_ITEM
--

CREATE TABLE MEASURE_CONTEXT_ITEM(
    MEASURE_CONTEXT_ITEM_ID          NUMBER(19, 0)    NOT NULL,
    GROUP_MEASURE_CONTEXT_ITEM_ID    NUMBER(19, 0),
    ASSAY_ID                         NUMBER(19, 0)    NOT NULL,
    MEASURE_CONTEXT_ID               NUMBER(19, 0),
    ATTRIBUTE_TYPE                   VARCHAR2(20)     NOT NULL
                                     CONSTRAINT CK_ATTRIBUTE_TYPE CHECK (ATTRIBUTE_TYPE in ('Fixed', 'List', 'Range', 'Number')),
    ATTRIBUTE_ID                     NUMBER(19, 0)    NOT NULL,
    QUALIFIER                        CHAR(2)
                                     CONSTRAINT CK_MEASURE_CONTEXT_ITEM_QALFR CHECK (QUALIFIER IN ('= ', '< ', '<=', '> ', '>=', '<<', '>>', '~ ')),
    VALUE_ID                         NUMBER(19, 0),
    VALUE_DISPLAY                    VARCHAR2(256),
    VALUE_NUM                        FLOAT(126),
    VALUE_MIN                        FLOAT(126),
    VALUE_MAX                        FLOAT(126),
    VERSION                          NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created                     TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated                     TIMESTAMP(6),
    MODIFIED_BY                      VARCHAR2(40),
    CONSTRAINT PK_MEASURE_CONTEXT_ITEM PRIMARY KEY (MEASURE_CONTEXT_ITEM_ID)
)
;



COMMENT ON COLUMN MEASURE_CONTEXT_ITEM.ASSAY_ID IS 'This column is useful for creating the assay defintion before the measures and their contexts have been properly grouped.'
;
COMMENT ON COLUMN MEASURE_CONTEXT_ITEM.VALUE_DISPLAY IS 'This is not a general text entry field, rather it is an easily displayable text version of the other value columns'
;
COMMENT ON COLUMN MEASURE_CONTEXT_ITEM.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: ONTOLOGY
--

CREATE TABLE ONTOLOGY(
    ONTOLOGY_ID      NUMBER(19, 0)     NOT NULL,
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



COMMENT ON TABLE ONTOLOGY IS 'an external ontology or dictionary or other source of reference data'
;
COMMENT ON COLUMN ONTOLOGY.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: ONTOLOGY_ITEM
--

CREATE TABLE ONTOLOGY_ITEM(
    ONTOLOGY_ITEM_ID    NUMBER(19, 0)    NOT NULL,
    ONTOLOGY_ID         NUMBER(19, 0)    NOT NULL,
    ELEMENT_ID          NUMBER(19, 0),
    ITEM_REFERENCE      CHAR(10),
    VERSION             NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created        TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated        TIMESTAMP(6),
    MODIFIED_BY         VARCHAR2(40),
    CONSTRAINT PK_ONTOLOGY_ITEM PRIMARY KEY (ONTOLOGY_ITEM_ID)
)
;



COMMENT ON COLUMN ONTOLOGY_ITEM.ITEM_REFERENCE IS 'Concatenate this with the Ontology.system_URL for a full URI for the item'
;
COMMENT ON COLUMN ONTOLOGY_ITEM.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: PROJECT
--

CREATE TABLE PROJECT(
    PROJECT_ID              NUMBER(19, 0)     NOT NULL,
    PROJECT_NAME            VARCHAR2(256)     NOT NULL,
    GROUP_TYPE              VARCHAR2(20)      DEFAULT 'Project' NOT NULL
                            CONSTRAINT CK_PROJECT_TYPE CHECK (GROUP_TYPE in ('Project', 'Campaign', 'Panel', 'Study')),
    DESCRIPTION             VARCHAR2(1000),
    ready_for_extraction    VARCHAR2(20)      DEFAULT 'Ready' NOT NULL
                            CONSTRAINT CK_PROJECT_EXTRACTION CHECK (ready_for_extraction IN ('Ready', 'Started', 'Complete')),
    VERSION                 NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created            TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated            TIMESTAMP(6),
    MODIFIED_BY             VARCHAR2(40),
    CONSTRAINT PK_PROJECT PRIMARY KEY (PROJECT_ID)
)
;



COMMENT ON COLUMN PROJECT.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: PROJECT_ASSAY
--

CREATE TABLE PROJECT_ASSAY(
    PROJECT_ASSAY_ID       NUMBER(19, 0)     NOT NULL,
    PROJECT_ID             NUMBER(19, 0)     NOT NULL,
    ASSAY_ID               NUMBER(19, 0)     NOT NULL,
    Stage_ID               NUMBER(19, 0)     NOT NULL,
    RELATED_ASSAY_ID       NUMBER(19, 0),
    SEQUENCE_NO            NUMBER(38, 0),
    PROMOTION_THRESHOLD    FLOAT(126),
    PROMOTION_CRITERIA     VARCHAR2(1000),
    VERSION                NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    Date_Created           TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    Last_Updated           TIMESTAMP(6),
    MODIFIED_BY            VARCHAR2(40),
    CONSTRAINT PK_PROJECT_ASSAY PRIMARY KEY (PROJECT_ASSAY_ID)
)
;



COMMENT ON COLUMN PROJECT_ASSAY.SEQUENCE_NO IS 'defines the promotion order (and often the running order) of a set of assays in a project'
;
COMMENT ON COLUMN PROJECT_ASSAY.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: RESULT
--

CREATE TABLE RESULT(
    RESULT_ID               NUMBER(19, 0)    NOT NULL,
    RESULT_STATUS           VARCHAR2(20)     DEFAULT 'Pending' NOT NULL
                            CONSTRAINT CK_RESULT_STATUS CHECK (Result_Status IN ('Pending', 'Approved', 'Rejected', 'Mark for Deletion')),
    READY_FOR_EXTRACTION    VARCHAR2(20)     DEFAULT 'Ready' NOT NULL
                            CONSTRAINT CK_RESULT_EXTRACTION CHECK (ready_for_extraction IN ('Ready', 'Started', 'Complete')),
    VALUE_DISPLAY           VARCHAR2(256),
    VALUE_NUM               FLOAT(126),
    VALUE_MIN               FLOAT(126),
    VALUE_MAX               FLOAT(126),
    QUALIFIER               CHAR(2)
                            CONSTRAINT CK_RESULT_QUALIFIER CHECK (Qualifier IN ('= ', '< ', '<=', '> ', '>=', '<<', '>>', '~ ')),
    EXPERIMENT_ID           NUMBER(19, 0)    NOT NULL,
    SUBSTANCE_ID            NUMBER(19, 0)    NOT NULL,
    RESULT_TYPE_ID          NUMBER(19, 0)    NOT NULL,
    VERSION                 NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created            TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated            TIMESTAMP(6),
    MODIFIED_BY             VARCHAR2(40),
    CONSTRAINT PK_RESULT PRIMARY KEY (RESULT_ID)
)
;



COMMENT ON COLUMN RESULT.SUBSTANCE_ID IS 'Has external reference to the PubChem SID'
;
COMMENT ON COLUMN RESULT.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: RESULT_CONTEXT_ITEM
--

CREATE TABLE RESULT_CONTEXT_ITEM(
    RESULT_CONTEXT_ITEM_ID     NUMBER(19, 0)    NOT NULL,
    GROUP_RESULT_CONTEXT_ID    NUMBER(19, 0),
    EXPERIMENT_ID              NUMBER(19, 0)    NOT NULL,
    RESULT_ID                  NUMBER(19, 0),
    ATTRIBUTE_ID               NUMBER(19, 0)    NOT NULL,
    VALUE_ID                   NUMBER(19, 0),
    QUALIFIER                  CHAR(2)
                               CONSTRAINT CK_RESULT_CONTEXT_ITEM_QUALFR CHECK (Qualifier IN ('= ', '< ', '<=', '> ', '>=', '<<', '>>', '~ ')),
    VALUE_DISPLAY              VARCHAR2(256),
    VALUE_NUM                  FLOAT(126),
    VALUE_MIN                  FLOAT(126),
    VALUE_MAX                  FLOAT(126),
    VERSION                    NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created               TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated               TIMESTAMP(6),
    MODIFIED_BY                VARCHAR2(40),
    CONSTRAINT PK_Result_context_item PRIMARY KEY (RESULT_CONTEXT_ITEM_ID)
)
;



COMMENT ON COLUMN RESULT_CONTEXT_ITEM.VALUE_DISPLAY IS 'This is not a general text entry field, rather it is an easily displayable text version of the other value columns'
;
COMMENT ON COLUMN RESULT_CONTEXT_ITEM.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: RESULT_HIERARCHY
--

CREATE TABLE RESULT_HIERARCHY(
    RESULT_ID           NUMBER(19, 0)    NOT NULL,
    PARENT_RESULT_ID    NUMBER(19, 0)    NOT NULL,
    HIERARCHY_TYPE      VARCHAR2(10)     NOT NULL
                        CONSTRAINT CK_RESULT_HIERARCHY_TYPE CHECK (HIERARCHY_TYPE in ('Child', 'Derives')),
    VERSION             NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created        TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated        TIMESTAMP(6),
    MODIFIED_BY         VARCHAR2(40),
    CONSTRAINT PK_RESULT_HIERARCHY PRIMARY KEY (RESULT_ID, PARENT_RESULT_ID)
)
;



COMMENT ON COLUMN RESULT_HIERARCHY.HIERARCHY_TYPE IS 'two types of hierarchy are allowed: parent/child where one result is dependant on or grouped with another; derived from where aresult is used to claculate another (e.g. PI used for IC50).  The hierarchy types are mutually exclusive.'
;
COMMENT ON COLUMN RESULT_HIERARCHY.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: RESULT_TYPE
--

CREATE TABLE RESULT_TYPE(
    NODE_ID               NUMBER(19, 0)     NOT NULL,
    Parent_node_id        NUMBER(19, 0),
    RESULT_TYPE_ID        NUMBER(19, 0)     NOT NULL,
    RESULT_TYPE_STATUS    VARCHAR2(20)      NOT NULL,
    RESULT_TYPE_NAME      VARCHAR2(128)     NOT NULL,
    DESCRIPTION           VARCHAR2(1000),
    ABBREVIATION          VARCHAR2(20),
    SYNONYMS              VARCHAR2(1000),
    BASE_UNIT             VARCHAR2(128),
    CONSTRAINT PK_RESULT_TYPE PRIMARY KEY (NODE_ID)
)
;



--
-- TABLE: STAGE
--

CREATE TABLE STAGE(
    NODE_ID           NUMBER(19, 0)     NOT NULL,
    PARENT_NODE_ID    NUMBER(19, 0),
    STAGE_ID          NUMBER(19, 0)     NOT NULL,
    STAGE_STATUS      VARCHAR2(20)      NOT NULL,
    STAGE             VARCHAR2(128)     NOT NULL,
    DESCRIPTION       VARCHAR2(1000),
    CONSTRAINT PK_STAGE PRIMARY KEY (NODE_ID)
)
;



--
-- TABLE: SUBSTANCE
--

CREATE TABLE SUBSTANCE(
    SUBSTANCE_ID        NUMBER(19, 0)     NOT NULL,
    COMPOUND_ID         NUMBER(38, 0),
    SMILES              VARCHAR2(4000),
    MOLECULAR_WEIGHT    NUMBER(10, 3),
    SUBSTANCE_TYPE      VARCHAR2(20)      NOT NULL
                        CONSTRAINT CK_SUBSTANCE_TYPE CHECK (Substance_Type in ('small molecule', 'protein', 'peptide', 'antibody', 'cell', 'oligonucleotide')),
    VERSION             NUMBER(38, 0)     DEFAULT 0 NOT NULL,
    DATE_CREATED        TIMESTAMP(6)      DEFAULT sysdate NOT NULL,
    LAST_UPDATED        TIMESTAMP(6),
    MODIFIED_BY         VARCHAR2(40),
    CONSTRAINT PK_SUBSTANCE PRIMARY KEY (SUBSTANCE_ID)
)
;



COMMENT ON COLUMN SUBSTANCE.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: TREE_ROOT
--

CREATE TABLE TREE_ROOT(
    TREE_ROOT_ID         NUMBER(19, 0)    NOT NULL,
    TREE_NAME            VARCHAR2(30)     NOT NULL,
    ELEMENT_ID           NUMBER(19, 0)    NOT NULL,
    RELATIONSHIP_TYPE    VARCHAR2(20),
    VERSION              NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    DATE_CREATED         TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    LAST_UPDATED         TIMESTAMP(6),
    MODIFIED_BY          VARCHAR2(40),
    CONSTRAINT PK_TREE_ROOT PRIMARY KEY (TREE_ROOT_ID)
)
;



COMMENT ON COLUMN TREE_ROOT.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- TABLE: UNIT
--

CREATE TABLE UNIT(
    NODE_ID           NUMBER(19, 0)     NOT NULL,
    PARENT_NODE_ID    NUMBER(19, 0),
    UNIT_ID           NUMBER(19, 0)     NOT NULL,
    UNIT              VARCHAR2(128)     NOT NULL,
    DESCRIPTION       VARCHAR2(1000),
    CONSTRAINT PK_UNIT PRIMARY KEY (NODE_ID)
)
;



--
-- TABLE: UNIT_CONVERSION
--

CREATE TABLE UNIT_CONVERSION(
    FROM_UNIT       VARCHAR2(128)    NOT NULL,
    TO_UNIT         VARCHAR2(128)    NOT NULL,
    MULTIPLIER      FLOAT(126),
    OFFSET          FLOAT(126),
    FORMULA         VARCHAR2(256),
    VERSION         NUMBER(38, 0)    DEFAULT 0 NOT NULL,
    Date_Created    TIMESTAMP(6)     DEFAULT sysdate NOT NULL,
    Last_Updated    TIMESTAMP(6),
    MODIFIED_BY     VARCHAR2(40),
    CONSTRAINT PK_UNIT_CONVERSION PRIMARY KEY (FROM_UNIT, TO_UNIT)
)
;



COMMENT ON COLUMN UNIT_CONVERSION.VERSION IS 'Update_version is used by Hibernate to resolve the "lost Update" problem when used in optimistic locking'
;
--
-- INDEX: IDX_ASSAY_NAME_VERSION
--

CREATE INDEX IDX_ASSAY_NAME_VERSION ON ASSAY(ASSAY_NAME, ASSAY_VERSION)
;
--
-- INDEX: FK_ASSAY_DESCRIPTOR_PARENT_SLF
--

CREATE INDEX FK_ASSAY_DESCRIPTOR_PARENT_SLF ON ASSAY_DESCRIPTOR(PARENT_NODE_ID)
;
--
-- INDEX: FK_PROTOCOL_ASSAY
--

CREATE INDEX FK_PROTOCOL_ASSAY ON Assay_document(ASSAY_ID)
;
--
-- INDEX: FK_BIOLOGY_DESCRIPTOR_PRNT_SLF
--

CREATE INDEX FK_BIOLOGY_DESCRIPTOR_PRNT_SLF ON BIOLOGY_DESCRIPTOR(PARENT_NODE_ID)
;
--
-- INDEX: FK_ELEMENT_UNIT
--

CREATE INDEX FK_ELEMENT_UNIT ON ELEMENT(UNIT)
;
--
-- INDEX: UI_ELEMENT_LOWER_LABEL
--

CREATE UNIQUE INDEX UI_ELEMENT_LOWER_LABEL ON ELEMENT(LOWER("LABEL"))
;
--
-- INDEX: AK_ELEMENT_HIERARCHY
--

CREATE UNIQUE INDEX AK_ELEMENT_HIERARCHY ON ELEMENT_HIERARCHY(CHILD_ELEMENT_ID, PARENT_ELEMENT_ID, RELATIONSHIP_TYPE)
;
--
-- INDEX: FK_E_HIERARCHY_PARENT_ELEM_ID
--

CREATE INDEX FK_E_HIERARCHY_PARENT_ELEM_ID ON ELEMENT_HIERARCHY(PARENT_ELEMENT_ID)
;
--
-- INDEX: FK_E_HIERARCHY_CHILD_ELEM_ID
--

CREATE INDEX FK_E_HIERARCHY_CHILD_ELEM_ID ON ELEMENT_HIERARCHY(CHILD_ELEMENT_ID)
;
--
-- INDEX: FK_EXPERIMENT_ASSAY
--

CREATE INDEX FK_EXPERIMENT_ASSAY ON EXPERIMENT(ASSAY_ID)
;
--
-- INDEX: FK_PROJECT_EXPERIMENT
--

CREATE INDEX FK_PROJECT_EXPERIMENT ON EXPERIMENT(PROJECT_ID)
;
--
-- INDEX: FK_EXT_ASSAY_ASSAY
--

CREATE INDEX FK_EXT_ASSAY_ASSAY ON EXTERNAL_ASSAY(ASSAY_ID)
;
--
-- INDEX: "FK_EXT_ASSAY_EXT_SYSTEM"
--

CREATE INDEX "FK_EXT_ASSAY_EXT_SYSTEM" ON EXTERNAL_ASSAY(EXTERNAL_SYSTEM_ID)
;
--
-- INDEX: FK_INSTANCE_DESCRIPTR_PRNT_SLF
--

CREATE INDEX FK_INSTANCE_DESCRIPTR_PRNT_SLF ON INSTANCE_DESCRIPTOR(PARENT_NODE_ID)
;
--
-- INDEX: FK_MEASURE_RESULT_TYPE
--

CREATE INDEX FK_MEASURE_RESULT_TYPE ON MEASURE(RESULT_TYPE_ID)
;
--
-- INDEX: FK_MEASURE_M_CONTEXT
--

CREATE INDEX FK_MEASURE_M_CONTEXT ON MEASURE(MEASURE_CONTEXT_ID)
;
--
-- INDEX: FK_MEASURE_ELEMENT_UNIT
--

CREATE INDEX FK_MEASURE_ELEMENT_UNIT ON MEASURE(ENTRY_UNIT)
;
--
-- INDEX: FK_MEASURE_ASSAY
--

CREATE INDEX FK_MEASURE_ASSAY ON MEASURE(ASSAY_ID)
;
--
-- INDEX: FK_MEASURE_PARENT_MEASURE_ID
--

CREATE INDEX FK_MEASURE_PARENT_MEASURE_ID ON MEASURE(PARENT_MEASURE_ID)
;
--
-- INDEX: AK_MEASURE_CONTEXT_ITEM
--

CREATE UNIQUE INDEX AK_MEASURE_CONTEXT_ITEM ON MEASURE_CONTEXT_ITEM(MEASURE_CONTEXT_ID, GROUP_MEASURE_CONTEXT_ITEM_ID, ATTRIBUTE_ID, ATTRIBUTE_TYPE, VALUE_DISPLAY)
;
--
-- INDEX: FK_M_CONTEXT_ITEM_M_CONTEXT
--

CREATE INDEX FK_M_CONTEXT_ITEM_M_CONTEXT ON MEASURE_CONTEXT_ITEM(MEASURE_CONTEXT_ID)
;
--
-- INDEX: FK_M_CONTEXT_ITEM_ATTRIBUTE
--

CREATE INDEX FK_M_CONTEXT_ITEM_ATTRIBUTE ON MEASURE_CONTEXT_ITEM(ATTRIBUTE_ID)
;
--
-- INDEX: FK_M_CONTEXT_ITEM_VALUE
--

CREATE INDEX FK_M_CONTEXT_ITEM_VALUE ON MEASURE_CONTEXT_ITEM(VALUE_ID)
;
--
-- INDEX: FK_M_CONTEXT_ITEM_ASSAY
--

CREATE INDEX FK_M_CONTEXT_ITEM_ASSAY ON MEASURE_CONTEXT_ITEM(ASSAY_ID)
;
--
-- INDEX: FK_M_CONTEXT_ITEM_QUALIFIER
--

CREATE INDEX FK_M_CONTEXT_ITEM_QUALIFIER ON MEASURE_CONTEXT_ITEM(QUALIFIER)
;
--
-- INDEX: FK_MEASURE_CONTEXT_ITEM_GROUP
--

CREATE INDEX FK_MEASURE_CONTEXT_ITEM_GROUP ON MEASURE_CONTEXT_ITEM(GROUP_MEASURE_CONTEXT_ITEM_ID)
;
--
-- INDEX: FK_ONTOLOGY_ITEM_ONTOLOGY
--

CREATE INDEX FK_ONTOLOGY_ITEM_ONTOLOGY ON ONTOLOGY_ITEM(ONTOLOGY_ID)
;
--
-- INDEX: FK_ONTOLOGY_ITEM_ELEMENT
--

CREATE INDEX FK_ONTOLOGY_ITEM_ELEMENT ON ONTOLOGY_ITEM(ELEMENT_ID)
;
--
-- INDEX: FK_PROJECT_ASSAY_ASSAY
--

CREATE INDEX FK_PROJECT_ASSAY_ASSAY ON PROJECT_ASSAY(ASSAY_ID)
;
--
-- INDEX: FK_PROJECT_ASSAY_PROJECT
--

CREATE INDEX FK_PROJECT_ASSAY_PROJECT ON PROJECT_ASSAY(PROJECT_ID)
;
--
-- INDEX: FK_PROJECT_ASSAY_RELATED_ASSAY
--

CREATE INDEX FK_PROJECT_ASSAY_RELATED_ASSAY ON PROJECT_ASSAY(RELATED_ASSAY_ID)
;
--
-- INDEX: FK_RESULT_EXPERIMENT
--

CREATE INDEX FK_RESULT_EXPERIMENT ON RESULT(EXPERIMENT_ID)
;
--
-- INDEX: FK_RESULT_SUBSTANCE
--

CREATE INDEX FK_RESULT_SUBSTANCE ON RESULT(SUBSTANCE_ID)
;
--
-- INDEX: FK_RESULT_RESULT_TYPE
--

CREATE INDEX FK_RESULT_RESULT_TYPE ON RESULT(RESULT_TYPE_ID)
;
--
-- INDEX: FK_RESULT_QUALIFIER
--

CREATE INDEX FK_RESULT_QUALIFIER ON RESULT(QUALIFIER)
;
--
-- INDEX: FK_R_CONTEXT_ITEM_EXPERIMENT
--

CREATE INDEX FK_R_CONTEXT_ITEM_EXPERIMENT ON RESULT_CONTEXT_ITEM(EXPERIMENT_ID)
;
--
-- INDEX: FK_R_CONTEXT_ITEM_ATTRIBUTE
--

CREATE INDEX FK_R_CONTEXT_ITEM_ATTRIBUTE ON RESULT_CONTEXT_ITEM(ATTRIBUTE_ID)
;
--
-- INDEX: FK_R_CONTEXT_ITEM_VALUE
--

CREATE INDEX FK_R_CONTEXT_ITEM_VALUE ON RESULT_CONTEXT_ITEM(VALUE_ID)
;
--
-- INDEX: FK_R_CONTEXT_ITEM_QUALIFIER
--

CREATE INDEX FK_R_CONTEXT_ITEM_QUALIFIER ON RESULT_CONTEXT_ITEM(QUALIFIER)
;
--
-- INDEX: FK_RESULT_CONTEXT_ITEM_RESULT
--

CREATE INDEX FK_RESULT_CONTEXT_ITEM_RESULT ON RESULT_CONTEXT_ITEM(RESULT_ID)
;
--
-- INDEX: FK_RESULT_CONTEXT_ITEM_GROUP
--

CREATE INDEX FK_RESULT_CONTEXT_ITEM_GROUP ON RESULT_CONTEXT_ITEM(GROUP_RESULT_CONTEXT_ID)
;
--
-- INDEX: FK_RESULT_HIERARCHY_RSLT_PRNT
--

CREATE INDEX FK_RESULT_HIERARCHY_RSLT_PRNT ON RESULT_HIERARCHY(RESULT_ID)
;
--
-- INDEX: FK_RESULT_HIERARCHY_RESULT
--

CREATE INDEX FK_RESULT_HIERARCHY_RESULT ON RESULT_HIERARCHY(PARENT_RESULT_ID)
;
--
-- INDEX: FK_RESULT_TYPE_PARENT_SELF
--

CREATE INDEX FK_RESULT_TYPE_PARENT_SELF ON RESULT_TYPE(Parent_node_id)
;
--
-- INDEX: AK_TREE_ROOT_NAME
--

CREATE UNIQUE INDEX AK_TREE_ROOT_NAME ON TREE_ROOT(TREE_NAME)
;
--
-- INDEX: FK_TREE_ROOT_ELEMENT
--

CREATE INDEX FK_TREE_ROOT_ELEMENT ON TREE_ROOT(ELEMENT_ID)
;
--
-- INDEX: FK_UNIT_PARENT_SELF
--

CREATE INDEX FK_UNIT_PARENT_SELF ON UNIT(PARENT_NODE_ID)
;
--
-- INDEX: FK_UNIT_CONVERSN_FRM_UNT_ELMNT
--

CREATE INDEX FK_UNIT_CONVERSN_FRM_UNT_ELMNT ON UNIT_CONVERSION(FROM_UNIT)
;
--
-- INDEX: FK_UNIT_CONVERSN_TO_UNT_ELMNT
--

CREATE INDEX FK_UNIT_CONVERSN_TO_UNT_ELMNT ON UNIT_CONVERSION(TO_UNIT)
;
--
-- TABLE: ELEMENT
--

ALTER TABLE ELEMENT ADD
    CONSTRAINT AK_ELEMENT_LABEL UNIQUE (LABEL)
;

--
-- TABLE: ASSAY_DESCRIPTOR
--

ALTER TABLE ASSAY_DESCRIPTOR ADD CONSTRAINT FK_ASSAY_DESCRIPTOR_PARENT_SLF
    FOREIGN KEY (PARENT_NODE_ID)
    REFERENCES ASSAY_DESCRIPTOR(NODE_ID)
;


--
-- TABLE: Assay_document
--

ALTER TABLE Assay_document ADD CONSTRAINT FK_Assay_document_assay
    FOREIGN KEY (ASSAY_ID)
    REFERENCES ASSAY(ASSAY_ID)
;


--
-- TABLE: BIOLOGY_DESCRIPTOR
--

ALTER TABLE BIOLOGY_DESCRIPTOR ADD CONSTRAINT FK_BIOLOGY_DESCRIPTOR_PRNT_SLF
    FOREIGN KEY (PARENT_NODE_ID)
    REFERENCES BIOLOGY_DESCRIPTOR(NODE_ID)
;


--
-- TABLE: ELEMENT
--

ALTER TABLE ELEMENT ADD CONSTRAINT FK_Element_Unit
    FOREIGN KEY (UNIT)
    REFERENCES ELEMENT(LABEL)
;


--
-- TABLE: ELEMENT_HIERARCHY
--

ALTER TABLE ELEMENT_HIERARCHY ADD CONSTRAINT FK_E_HIERARCHY_CHILD_ELEM_ID
    FOREIGN KEY (CHILD_ELEMENT_ID)
    REFERENCES ELEMENT(ELEMENT_ID) ON DELETE CASCADE
;

ALTER TABLE ELEMENT_HIERARCHY ADD CONSTRAINT FK_E_HIERARCHY_PARENT_ELEM_ID
    FOREIGN KEY (PARENT_ELEMENT_ID)
    REFERENCES ELEMENT(ELEMENT_ID) ON DELETE CASCADE
;


--
-- TABLE: EXPERIMENT
--

ALTER TABLE EXPERIMENT ADD CONSTRAINT FK_EXPERIMENT_ASSAY
    FOREIGN KEY (ASSAY_ID)
    REFERENCES ASSAY(ASSAY_ID)
;

ALTER TABLE EXPERIMENT ADD CONSTRAINT FK_EXPERIMENT_LABORATORY
    FOREIGN KEY (LABORATORY_ID)
    REFERENCES ELEMENT(ELEMENT_ID)
;

ALTER TABLE EXPERIMENT ADD CONSTRAINT FK_PROJECT_EXPERIMENT
    FOREIGN KEY (PROJECT_ID)
    REFERENCES PROJECT(PROJECT_ID)
;


--
-- TABLE: EXTERNAL_ASSAY
--

ALTER TABLE EXTERNAL_ASSAY ADD CONSTRAINT FK_EXT_ASSAY_ASSAY
    FOREIGN KEY (ASSAY_ID)
    REFERENCES ASSAY(ASSAY_ID)
;

ALTER TABLE EXTERNAL_ASSAY ADD CONSTRAINT FK_EXT_ASSAY_EXT_SYSTEM
    FOREIGN KEY (EXTERNAL_SYSTEM_ID)
    REFERENCES EXTERNAL_SYSTEM(EXTERNAL_SYSTEM_ID)
;


--
-- TABLE: INSTANCE_DESCRIPTOR
--

ALTER TABLE INSTANCE_DESCRIPTOR ADD CONSTRAINT FK_INSTANCE_DESCRIPTR_PRNT_SLF
    FOREIGN KEY (PARENT_NODE_ID)
    REFERENCES INSTANCE_DESCRIPTOR(NODE_ID)
;


--
-- TABLE: LABORATORY
--

ALTER TABLE LABORATORY ADD CONSTRAINT FK_LABORATORY_PARENT_NODE_ID
    FOREIGN KEY (PARENT_NODE_ID)
    REFERENCES LABORATORY(NODE_ID)
;


--
-- TABLE: MEASURE
--

ALTER TABLE MEASURE ADD CONSTRAINT FK_MEASURE_ASSAY
    FOREIGN KEY (ASSAY_ID)
    REFERENCES ASSAY(ASSAY_ID)
;

ALTER TABLE MEASURE ADD CONSTRAINT FK_Measure_Element_Unit
    FOREIGN KEY (ENTRY_UNIT)
    REFERENCES ELEMENT(LABEL)
;

ALTER TABLE MEASURE ADD CONSTRAINT FK_MEASURE_M_CONTEXT
    FOREIGN KEY (MEASURE_CONTEXT_ID)
    REFERENCES MEASURE_CONTEXT(MEASURE_CONTEXT_ID)
;

ALTER TABLE MEASURE ADD CONSTRAINT FK_MEASURE_PARENT_MEASURE_ID
    FOREIGN KEY (PARENT_MEASURE_ID)
    REFERENCES MEASURE(MEASURE_ID)
;

ALTER TABLE MEASURE ADD CONSTRAINT FK_MEASURE_RESULT_TYPE
    FOREIGN KEY (RESULT_TYPE_ID)
    REFERENCES ELEMENT(ELEMENT_ID)
;


--
-- TABLE: MEASURE_CONTEXT
--

ALTER TABLE MEASURE_CONTEXT ADD CONSTRAINT FK_MEASURE_CONTEXT_ASSAY
    FOREIGN KEY (ASSAY_ID)
    REFERENCES ASSAY(ASSAY_ID)
;


--
-- TABLE: MEASURE_CONTEXT_ITEM
--

ALTER TABLE MEASURE_CONTEXT_ITEM ADD CONSTRAINT FK_M_CONTEXT_ITEM_ASSAY
    FOREIGN KEY (ASSAY_ID)
    REFERENCES ASSAY(ASSAY_ID)
;

ALTER TABLE MEASURE_CONTEXT_ITEM ADD CONSTRAINT FK_M_CONTEXT_ITEM_ATTRIBUTE
    FOREIGN KEY (ATTRIBUTE_ID)
    REFERENCES ELEMENT(ELEMENT_ID)
;

ALTER TABLE MEASURE_CONTEXT_ITEM ADD CONSTRAINT FK_M_CONTEXT_ITEM_M_CONTEXT
    FOREIGN KEY (MEASURE_CONTEXT_ID)
    REFERENCES MEASURE_CONTEXT(MEASURE_CONTEXT_ID)
;

ALTER TABLE MEASURE_CONTEXT_ITEM ADD CONSTRAINT FK_M_CONTEXT_ITEM_VALUE
    FOREIGN KEY (VALUE_ID)
    REFERENCES ELEMENT(ELEMENT_ID)
;

ALTER TABLE MEASURE_CONTEXT_ITEM ADD CONSTRAINT FK_MEASURE_CONTEXT_ITEM_GROUP
    FOREIGN KEY (GROUP_MEASURE_CONTEXT_ITEM_ID)
    REFERENCES MEASURE_CONTEXT_ITEM(MEASURE_CONTEXT_ITEM_ID)
;


--
-- TABLE: ONTOLOGY_ITEM
--

ALTER TABLE ONTOLOGY_ITEM ADD CONSTRAINT FK_ONTOLOGY_ITEM_ELEMENT
    FOREIGN KEY (ELEMENT_ID)
    REFERENCES ELEMENT(ELEMENT_ID) ON DELETE CASCADE
;

ALTER TABLE ONTOLOGY_ITEM ADD CONSTRAINT FK_ONTOLOGY_ITEM_ONTOLOGY
    FOREIGN KEY (ONTOLOGY_ID)
    REFERENCES ONTOLOGY(ONTOLOGY_ID)
;


--
-- TABLE: PROJECT_ASSAY
--

ALTER TABLE PROJECT_ASSAY ADD CONSTRAINT FK_PROJECT_ASSAY_ASSAY
    FOREIGN KEY (ASSAY_ID)
    REFERENCES ASSAY(ASSAY_ID)
;

ALTER TABLE PROJECT_ASSAY ADD CONSTRAINT FK_PROJECT_ASSAY_PROJECT
    FOREIGN KEY (PROJECT_ID)
    REFERENCES PROJECT(PROJECT_ID)
;

ALTER TABLE PROJECT_ASSAY ADD CONSTRAINT FK_Project_Assay_Related_Assay
    FOREIGN KEY (RELATED_ASSAY_ID)
    REFERENCES ASSAY(ASSAY_ID)
;

ALTER TABLE PROJECT_ASSAY ADD CONSTRAINT FK_PROJECT_ASSAY_STAGE
    FOREIGN KEY (Stage_ID)
    REFERENCES ELEMENT(ELEMENT_ID)
;


--
-- TABLE: RESULT
--

ALTER TABLE RESULT ADD CONSTRAINT FK_RESULT_EXPERIMENT
    FOREIGN KEY (EXPERIMENT_ID)
    REFERENCES EXPERIMENT(EXPERIMENT_ID)
;

ALTER TABLE RESULT ADD CONSTRAINT FK_Result_Result_Type
    FOREIGN KEY (RESULT_TYPE_ID)
    REFERENCES ELEMENT(ELEMENT_ID)
;


--
-- TABLE: RESULT_CONTEXT_ITEM
--

ALTER TABLE RESULT_CONTEXT_ITEM ADD CONSTRAINT FK_R_CONTEXT_ITEM_ATTRIBUTE
    FOREIGN KEY (ATTRIBUTE_ID)
    REFERENCES ELEMENT(ELEMENT_ID)
;

ALTER TABLE RESULT_CONTEXT_ITEM ADD CONSTRAINT FK_R_CONTEXT_ITEM_EXPERIMENT
    FOREIGN KEY (EXPERIMENT_ID)
    REFERENCES EXPERIMENT(EXPERIMENT_ID)
;

ALTER TABLE RESULT_CONTEXT_ITEM ADD CONSTRAINT FK_R_CONTEXT_ITEM_VALUE
    FOREIGN KEY (VALUE_ID)
    REFERENCES ELEMENT(ELEMENT_ID)
;

ALTER TABLE RESULT_CONTEXT_ITEM ADD CONSTRAINT FK_RESULT_CONTEXT_ITEM_GROUP
    FOREIGN KEY (GROUP_RESULT_CONTEXT_ID)
    REFERENCES RESULT_CONTEXT_ITEM(RESULT_CONTEXT_ITEM_ID)
;

ALTER TABLE RESULT_CONTEXT_ITEM ADD CONSTRAINT FK_RESULT_CONTEXT_ITEM_RESULT
    FOREIGN KEY (RESULT_ID)
    REFERENCES RESULT(RESULT_ID)
;


--
-- TABLE: RESULT_HIERARCHY
--

ALTER TABLE RESULT_HIERARCHY ADD CONSTRAINT FK_RESULT_HIERARCHY_RESULT
    FOREIGN KEY (PARENT_RESULT_ID)
    REFERENCES RESULT(RESULT_ID)
;

ALTER TABLE RESULT_HIERARCHY ADD CONSTRAINT FK_RESULT_HIERARCHY_RSLT_PRNT
    FOREIGN KEY (RESULT_ID)
    REFERENCES RESULT(RESULT_ID)
;


--
-- TABLE: RESULT_TYPE
--

ALTER TABLE RESULT_TYPE ADD CONSTRAINT FK_Result_Type_Parent_Self
    FOREIGN KEY (Parent_node_id)
    REFERENCES RESULT_TYPE(NODE_ID)
;


--
-- TABLE: STAGE
--

ALTER TABLE STAGE ADD CONSTRAINT FK_STAGE_PARENT_NODE_ID
    FOREIGN KEY (PARENT_NODE_ID)
    REFERENCES STAGE(NODE_ID)
;


--
-- TABLE: TREE_ROOT
--

ALTER TABLE TREE_ROOT ADD CONSTRAINT FK_TREE_ROOT_ELEMENT
    FOREIGN KEY (ELEMENT_ID)
    REFERENCES ELEMENT(ELEMENT_ID)
;


--
-- TABLE: UNIT
--

ALTER TABLE UNIT ADD CONSTRAINT FK_UNIT_PARENT_SELF
    FOREIGN KEY (PARENT_NODE_ID)
    REFERENCES UNIT(NODE_ID)
;


--
-- TABLE: UNIT_CONVERSION
--

ALTER TABLE UNIT_CONVERSION ADD CONSTRAINT FK_UNIT_CONVERSN_FRM_UNT_ELMNT
    FOREIGN KEY (FROM_UNIT)
    REFERENCES ELEMENT(LABEL)
;

ALTER TABLE UNIT_CONVERSION ADD CONSTRAINT FK_UNIT_CONVERSN_TO_UNT_ELMNT
    FOREIGN KEY (TO_UNIT)
    REFERENCES ELEMENT(LABEL)
;