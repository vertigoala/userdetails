import groovy.sql.Sql

//Print single row
println "\n------------------USERDETAILS CREATE ADMIN------------------n"

def sql = Sql.newInstance("jdbc:mysql://mysqldbcas:3306/emmet?noAccessToProcedureBodies=true&useSSL=false", "cas","myC@Sp@s\$w0rlD", "com.mysql.jdbc.Driver")

// wait until table user_role exists
//def i = 0
def hastable="SELECT count(*) as qtd FROM information_schema.tables WHERE table_schema = 'emmet' AND table_name = 'user_role' limit 1"
while (true) {
    //i++
    println "USERDETAILS CREATE ADMIN: Checking if 'user_role' table exists"
    def qtd = sql.firstRow("select count(*) as qtd from users where username='admin@example.com'").qtd
    if (qtd == 1) {
        println "USERDETAILS CREATE ADMIN: table 'user_role' found, continuing..."
        break
    } else {
        println "USERDETAILS CREATE ADMIN: table 'user_role' not found, sleeping..."
        sleep(5000)
    }
}

def insertRole="insert into user_role values (?,?)"
def row = sql.firstRow("select count(*) as qtd from users where username='admin@example.com'")
if (row.qtd == 0) {
    println "USERDETAILS CREATE ADMIN: Creating user for 'admin@example.com'"
    // IN: email,firstname,lastname,password,organisation,city,state,country
    // OUT: user_id
    def insertUser="{call sp_create_user(?,?,?,?,?,?,?,?,?)}"
    def lastid=0
    sql.call(insertUser,['admin@example.com','Admin','Admin','mypasswd','ALA','Rio de Janeiro','RJ','Brazil',Sql.VARCHAR]) { lastidstr ->
      lastid = lastidstr
    }
    sql.execute(insertRole,[lastid,'ROLE_ADMIN'])
    sql.execute(insertRole,[lastid,'ROLE_SYSTEM_ADMIN'])
    println "USERDETAILS CREATE ADMIN: user for 'admin@example.com' created"
} else {
    println "USERDETAILS CREATE ADMIN: User 'admin@example.com' already exists"
}

println "\n-------------END OF USERDETAILS CREATE ADMIN----------------n"
