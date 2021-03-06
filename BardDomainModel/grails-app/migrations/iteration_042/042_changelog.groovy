/* Copyright (c) 2014, The Broad Institute
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of The Broad Institute nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL The Broad Institute BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package iteration_042

databaseChangeLog = {

    changeSet(author: "ycruz", id: "iteration-042/00-populate-assay-approvedby-and-date", dbms: "oracle", context: "standard") {

        grailsChange {
            change {
                sql.execute("""BEGIN
                                   bard_context.set_username('ycruz');
                                   END;
                                   """)
                //Update code for Assay table
                String ALL_APPROVED_ASSAY_IDS = """SELECT ASSAY_ID FROM ASSAY WHERE ASSAY_STATUS = 'Approved'"""
                String LAST_APPROVED_RECORD = """SELECT ARL.AUDIT_TIMESTAMP, ARL.USERNAME AUDIT_USERNAME, P.USERNAME PERSON_USERNAME , P.FULL_NAME FROM AUDIT_ROW_LOG ARL JOIN AUDIT_COLUMN_LOG ACL ON ACL.AUDIT_ID = ARL.AUDIT_ID JOIN ASSAY A ON A.ASSAY_ID = ARL.PRIMARY_KEY LEFT OUTER JOIN PERSON P ON P.USERNAME = ARL.USERNAME WHERE ARL.TABLE_NAME = 'ASSAY' AND ACL.COLUMN_NAME = 'ASSAY_STATUS' AND ARL.ACTION = 'UPDATE' AND A.ASSAY_ID = ? ORDER BY ARL.AUDIT_TIMESTAMP DESC"""
                String

                println("Start - Updating assays' approvedBy and approvedTime ")
                int processedCount = 0
                sql.eachRow(ALL_APPROVED_ASSAY_IDS) { row ->
                    def assay_id = row.ASSAY_ID
                    def last_approved_row = sql.firstRow(LAST_APPROVED_RECORD, [assay_id])
                    if(last_approved_row){
                        def name = null
                        def full_name = last_approved_row.FULL_NAME
                        def audit_username = last_approved_row.AUDIT_USERNAME
                        def person_username = last_approved_row.PERSON_USERNAME
                        if(full_name){
                            name = full_name
                        }
                        else if(audit_username){
                            name = audit_username
                        }
                        else{
                            name = person_username
                        }
                        try{
                            String updateStatement = """UPDATE ASSAY SET APPROVED_BY=?, APPROVED_DATE=?  WHERE ASSAY_ID = ?"""
                            sql.executeUpdate(updateStatement, [name, last_approved_row.AUDIT_TIMESTAMP, assay_id])
                            processedCount++
                        }
                        catch(java.sql.SQLSyntaxErrorException e){
                            println("ERROR updating assay with ID: ${assay_id} -> ${e.message}")
                        }
                    }
                    else{
                        println("ASSAY_ID: ${assay_id} - No record found for this approval")
                    }
                }
                println("Finished - Updating ${processedCount} assays")


                //Update code for Project table
                String ALL_APPROVED_PROJECT_IDS = """SELECT PROJECT_ID FROM PROJECT WHERE PROJECT_STATUS = 'Approved'"""
                String LAST_APPROVED_RECORD_FOR_PROJECT = """SELECT ARL.AUDIT_TIMESTAMP, ARL.USERNAME AUDIT_USERNAME, P.USERNAME PERSON_USERNAME , P.FULL_NAME FROM AUDIT_ROW_LOG ARL JOIN AUDIT_COLUMN_LOG ACL ON ACL.AUDIT_ID = ARL.AUDIT_ID JOIN PROJECT PROJ ON PROJ.PROJECT_ID = ARL.PRIMARY_KEY LEFT OUTER JOIN PERSON P ON P.USERNAME = ARL.USERNAME WHERE ARL.TABLE_NAME = 'PROJECT' AND ACL.COLUMN_NAME = 'PROJECT_STATUS' AND ARL.ACTION = 'UPDATE' AND PROJ.PROJECT_ID = ? ORDER BY ARL.AUDIT_TIMESTAMP DESC"""

                println("Start - Updating projects' approvedBy and approvedTime ")
                int processedCountProj = 0
                sql.eachRow(ALL_APPROVED_PROJECT_IDS) { row ->
                    def project_id = row.PROJECT_ID
                    def last_approved_row = sql.firstRow(LAST_APPROVED_RECORD_FOR_PROJECT, [project_id])
                    if(last_approved_row){
                        def name = null
                        def full_name = last_approved_row.FULL_NAME
                        def audit_username = last_approved_row.AUDIT_USERNAME
                        def person_username = last_approved_row.PERSON_USERNAME
                        if(full_name){
                            name = full_name
                        }
                        else if(audit_username){
                            name = audit_username
                        }
                        else{
                            name = person_username
                        }
                        try{
                            String updateStatement = """UPDATE PROJECT SET APPROVED_BY=?, APPROVED_DATE=?  WHERE PROJECT_ID = ?"""
                            sql.executeUpdate(updateStatement, [name, last_approved_row.AUDIT_TIMESTAMP, project_id])
                            processedCountProj++
                        }
                        catch(java.sql.SQLException e){
                            println("ERROR updating project with ID: ${project_id} -> ${e.message}")
                        }
                    }
                    else{
                        println("PROJECT_ID: ${project_id} - No record found for this approval")
                    }
                }
                println("Finished - Updating ${processedCountProj} projects")


                //                Update code for Experiment table
                String ALL_APPROVED_EXPERIMENT_IDS = """SELECT EXPERIMENT_ID FROM EXPERIMENT WHERE EXPERIMENT_STATUS = 'Approved'"""
                String LAST_APPROVED_RECORD_FOR_EXPERIMENT = """SELECT ARL.AUDIT_TIMESTAMP, ARL.USERNAME AUDIT_USERNAME, P.USERNAME PERSON_USERNAME , P.FULL_NAME FROM AUDIT_ROW_LOG ARL JOIN AUDIT_COLUMN_LOG ACL ON ACL.AUDIT_ID = ARL.AUDIT_ID JOIN EXPERIMENT E ON E.EXPERIMENT_ID = ARL.PRIMARY_KEY LEFT OUTER JOIN PERSON P ON P.USERNAME = ARL.USERNAME WHERE ARL.TABLE_NAME = 'EXPERIMENT' AND ACL.COLUMN_NAME = 'EXPERIMENT_STATUS' AND ARL.ACTION = 'UPDATE' AND E.EXPERIMENT_ID = ? ORDER BY ARL.AUDIT_TIMESTAMP DESC"""

                println("Start - Updating experiments' approvedBy and approvedTime ")
                int processedCountExp = 0
                try{
                sql.eachRow(ALL_APPROVED_EXPERIMENT_IDS) { row ->
                    def experiment_id = row.EXPERIMENT_ID
                    def last_approved_row = sql.firstRow(LAST_APPROVED_RECORD_FOR_EXPERIMENT, [experiment_id])
                    if(last_approved_row){
                        def name = null
                        def full_name = last_approved_row.FULL_NAME
                        def audit_username = last_approved_row.AUDIT_USERNAME
                        def person_username = last_approved_row.PERSON_USERNAME
                        if(full_name){
                            name = full_name
                        }
                        else if(audit_username){
                            name = audit_username
                        }
                        else{
                            name = person_username
                        }
                        try{
                            String updateStatement = """UPDATE EXPERIMENT SET APPROVED_BY=?, APPROVED_DATE=?  WHERE EXPERIMENT_ID = ?"""
                            sql.executeUpdate(updateStatement, [name, last_approved_row.AUDIT_TIMESTAMP, experiment_id])
                            processedCountExp++
                        }
                        catch(java.sql.SQLSyntaxErrorException e){
                            println("ERROR updating experiment with ID: ${experiment_id} -> ${e.message}")
                        }
                    }
                    else{
                        println("EXPERIMENT_ID: ${experiment_id} - No record found for this approval")
                    }
                }
                }
                catch(java.sql.SQLException e){
                    println("ERROR executing sql for project - Message: ${e.message} -> ${e.printStackTrace()}")
                }
                println("Finished - Updating ${processedCountExp} experiments")
            }
        }
    }

    changeSet(author: 'pmontgom', id: 'iteration-042/01-substance-count-not-null', dbms: 'oracle', context: 'standard') {
        grailsChange {
            change {
                sql.execute("alter table experiment_file modify substance_count not null")
            }
        }
    }

    changeSet(author: 'ddurkin', id: 'iteration-042/03-update-expected-value-for-measured-component', dbms: 'oracle', context: 'standard') {
        grailsChange {
            change {
                sql.execute("""BEGIN
                               bard_context.set_username('ddurkin');
                               END;
                            """
                )
                sql.execute("update element set expected_value_type = 'free text' where label = 'measured component'")
            }
        }
    }

    changeSet(author: "pmontgom", id: "iteration-042/03-ready-for-extract-constraint", dbms: "oracle", context: "standard") {
        sqlFile(path: "iteration_042/03-ready-for-extract-constraint.sql", stripComments: true)
    }

}
