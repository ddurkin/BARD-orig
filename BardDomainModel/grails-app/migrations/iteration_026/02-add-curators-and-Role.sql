INSERT INTO PERSON (ACCOUNT_ENABLED, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, DATE_CREATED, FULL_NAME, LAST_UPDATED, MODIFIED_BY, USERNAME, PERSON_ID)
VALUES (0,0,0,sysdate,'Josh Bittker',sysdate,'jasiedu','jbittker',PERSON_ID_SEQ.NEXTVAL)
;
INSERT INTO PERSON_ROLE (LAST_UPDATED,MODIFIED_BY, PERSON_ID, PERSON_ROLE_ID, ROLE_ID)
(SELECT SYSDATE,'jasiedu',P.PERSON_ID,PERSON_ROLE_ID_SEQ.NEXTVAL, R.ROLE_ID FROM PERSON P,ROLE R WHERE
R.AUTHORITY='CURATOR' AND P.USERNAME='jbittker')
;
INSERT INTO PERSON (ACCOUNT_ENABLED, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, DATE_CREATED, FULL_NAME, LAST_UPDATED, MODIFIED_BY, USERNAME, PERSON_ID)
VALUES (0,0,0,SYSDATE,'David Lahr',SYSDATE,'jasiedu','dlahr',PERSON_ID_SEQ.NEXTVAL)
;
INSERT INTO PERSON_ROLE (LAST_UPDATED,MODIFIED_BY, PERSON_ID, PERSON_ROLE_ID, ROLE_ID)
(SELECT SYSDATE,'jasiedu',P.PERSON_ID,PERSON_ROLE_ID_SEQ.NEXTVAL, R.ROLE_ID FROM PERSON P,ROLE R WHERE
R.AUTHORITY='CURATOR' AND P.USERNAME='dlahr')
;
INSERT INTO PERSON (ACCOUNT_ENABLED, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, DATE_CREATED, FULL_NAME, LAST_UPDATED, MODIFIED_BY, USERNAME, PERSON_ID)
VALUES (0,0,0,SYSDATE,'Paul Clemons',SYSDATE,'jasiedu','pclemons',PERSON_ID_SEQ.NEXTVAL)
;
INSERT INTO PERSON_ROLE (LAST_UPDATED,MODIFIED_BY, PERSON_ID, PERSON_ROLE_ID, ROLE_ID)
(SELECT SYSDATE,'jasiedu',P.PERSON_ID,PERSON_ROLE_ID_SEQ.NEXTVAL, R.ROLE_ID FROM PERSON P,ROLE R WHERE
R.AUTHORITY='CURATOR' AND P.USERNAME='pclemons')
;
INSERT INTO PERSON (ACCOUNT_ENABLED, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, DATE_CREATED, FULL_NAME, LAST_UPDATED, MODIFIED_BY, USERNAME, PERSON_ID)
VALUES (0,0,0,SYSDATE,'Jason Rose',SYSDATE,'jasiedu','jasonr',PERSON_ID_SEQ.NEXTVAL)
;
INSERT INTO PERSON_ROLE (LAST_UPDATED,MODIFIED_BY, PERSON_ID, PERSON_ROLE_ID, ROLE_ID)
(SELECT SYSDATE,'jasiedu',P.PERSON_ID,PERSON_ROLE_ID_SEQ.NEXTVAL, R.ROLE_ID FROM PERSON P,ROLE R WHERE
R.AUTHORITY='CURATOR' AND P.USERNAME='jasonr')
;

