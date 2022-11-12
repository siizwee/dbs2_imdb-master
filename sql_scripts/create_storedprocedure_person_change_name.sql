CREATE OR REPLACE PROCEDURE person_change_name(old_name VARCHAR, new_name VARCHAR)
    IS
BEGIN
    UPDATE PERSON SET PERSON.NAME = new_name where PERSON.NAME = old_name;
    COMMIT;
END;