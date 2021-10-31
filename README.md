# web_JDBC

## 1. DBMS와 JDBC Driver 준비하기

1. Oracle을 사용하므로 오라클 홈페이지가서 데이터베이스와 자바 버전에 맞는 오라클 JDBC 다운
2. 이클립스 새 프로젝트 생성 후 프로젝트 오른쪽 클릭해서 Bulid Path -> configure Bulid Path -> Libraries 탭에 -> add External JARs -> 설치한 JDBC드라이버 추가

#### JDBC 기본 코드

      Class.forName("oracle.jdbc.driver.OracleDriver") // 메모리상에 드라이버가 올라감
      Connection con = DriverManager.getConnection(...);  // 연결이 이루어짐
      Statement st = con.createStatement(); // 사용자로부터 요구받은 쿼리를 실행
      ResultSet rs = st.exeuteQuery(sql); // 서버쪽에 결과집합이 만들어짐, 이용할 수 있는 준비가 됨, Before of File 부터 커서가 올라감
      rs.next(); // 커서가 밑으로 내려가서 1행의 내용을 가져옴
      String title = rs.getString("title"); // title 컬럼에 있는 것을 문자열로 주세요.

      rs.close(); // 역순으로 닫아줌
      st.close();
      con.close();
  
결과집합의 끝 End of File 까지 커서가 도착하면 결과집합에 있는 내용을 전부 확인한 것


## 2. 쿼리 실행하기
데이터는 오라클 강의에서 만들었던 테이블 참고

![image](https://user-images.githubusercontent.com/81665608/138865573-0361937d-a9cf-4dd0-909f-1f578190a89c.png)

#### 예제

    public class Program {

      public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:oracle:thin:@호스트이름:1521/xepdb1"; 
        String sql = "SELECT * FROM NOTICE";

        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(url, "ID","PWD");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        if(rs.next()) { //rs.next() 는 true 또는 false 반환
          String title = rs.getString("TITLE");  // 컬럼은 대소문자 구별 안해도됨
          System.out.println(title); // JDBC란 무엇인가?
        }
        rs.close();
        st.close();
        con.close();
      }
    }

### 2.1 데이터 가공은 SQL
1억개가 넘는 게시물이 있다고 가정하면 자바에서 필터링할 경우 1억개가 넘는 데이터를 쿼리해서 또 1억개가 넘는 데이터를 가공하는 것 보다 <br>
SQL에서 필터링 후 자바에서는 사용하기만 하는 방식으로 해야한다.

![image](https://user-images.githubusercontent.com/81665608/138873882-b3f6bd94-20c4-4389-b337-39968ba08663.png)

String sql = "SELECT * FROM NOTICE WHERE HIT >= 10"; 처럼 SQL 구문을 잘 활용

## 3. 트랜잭션
데이터베이스의 상태를 변환시키는 하나의 논리적 기능을 수행하기 위한 작업의 단위 또는 한꺼번에 모두 수행되어야 할 일련의 연산들을 의미<br>
예를 들어 게시판에 게시물을 게시하면 insert가 사용되고 그 후 select를 해서 최신 게시판 상태로 유지하는데 여기서 insert와 select문이 합쳐진 작업단위를 하나의 트랜잭션이라 한다.<br>

#### 트랜잭션의 특징은 크게 4가지로 구분된다. 

#### 원자성 (Atomicity)
- 트랜잭션이 그 실행을 성공적으로 수행된 후에도 언제나 일관성 있는 데이터베이스 상태로 유지해야 한다.
- 시스템이 가지고 있는 고정 요소는 트랜잭션 수행 전과 트랜잭션 수행 완료 후의 상태가 같아야 한다.

#### 일관성 (Consistency)
- 트랜잭션이 그 실행을 성공적으로 수행된 후에도 언제나 일관성 있는 데이터베이스 상태로 유지해야 한다.
- 시스템이 가지고 있는 고정 요소는 트랜잭션 수행 전과 트랜잭션 수행 완료 후의 상태가 같아야 한다.

#### 독립성 (Isolation)
- 둘 이상의 트랜잭션이 동시에 병행 실행되는 경우 어느 하나의 트랜잭션 실행 중에 다른 트랜잭션의 연산이 끼어들 수 없다.
- 수행 중인 트랜잭션은 완전히 완료될 때까지 다른 트랜잭션에서 수행 결과를 참조할 수 없다.

#### 지속성 (Durability)
- 트랜잭션이 성공적으로 완료된 트랜잭션의 결과는 시스템이 고장 나더라도 영구적으로 반영되어야 한다.

출처: https://junghn.tistory.com/entry/DataBase기초-트랜잭션이란-무엇인가-Transaction [코딩 시그널]

## 4. 데이터 입력하기와 PreparedStatement

오라클 tool에서 쿼리문을 복사해서 이클립스에 붙여넣으면 아래 처럼 나오는데

      String sql = "INSERT INTO notice (" + 
            "    writer_id," + 
            "    content," + 
            "    title" + 
            ") VALUES (" + 
            "    'qqq'," + 
            "    'test content'," + 
            "    'test'" + 
            ")";
            
 값을 변수로 바꾸고 쿼리문을 변경하면 
 
       String title = "";

       String sql = "INSERT INTO notice (" + 
                  "    writer_id," + 
                  "    content," + 
                  "    title" + 
                  ") VALUES (" + 
                  "    'qqq'," + 
                  "    'test content'," + 
                  "    '"+title+"'" +     // 하지만 이렇게 바꾸면 좀 복잡하다.
                  ")";
                  
 이 때 좀 편할 수 있게 사용하는 기능이 preparedStatement
 
      String writerId = "qqq";
      String content = "hahahaha";
      String title = "TEST2";

      String url = "jdbc:oracle:thin:@localhost:1521/xepdb1"; 
      String sql = "INSERT INTO NOTICE (" + 
                  "    writer_id," + 
                  "    content," + 
                  "    title" + 
                  ") VALUES (?,?,?)";  // 변수는 ?로 

      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection con = DriverManager.getConnection(url, "ID","PWD");
      //Statement st = con.createStatement(); 
      //ResultSet rs = st.executeQuery(sql); // 기존은 실행하면서 문장을 전달
      PreparedStatement st = con.prepareStatement(sql); // 실행하기 전에 문장을 준비
      
      st.setString(1, writerId); // index 1부터 시작
      st.setString(2, content);
      st.setString(3, title);

      int result = st.executeUpdate(); // insert 성공시 반환 값은 1
 
 ## 5. 데이터 수정하기 및 삭제하기
 
 ### 5.1 수정하기
 
      String writerId = "qqq";
      String content = "hahahaha33";
      String title = "TEST3";
      int id = 10;

      String url = "jdbc:oracle:thin:@localhost:1521/xepdb1"; 
      String sql = "UPDATE NOTICE " + 
            "SET" + 
            "    TITLE = ?," + 
            "    CONTENT = ?" + 
            "WHERE ID = ?";

      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection con = DriverManager.getConnection(url, "","");

      PreparedStatement st = con.prepareStatement(sql); 
      st.setString(1, title); // index 1부터 시작
      st.setString(2, content); 
      st.setInt(3, id);

      int result = st.executeUpdate(); 

      System.out.println(result); // 1

      st.close();
      con.close();
      
### 5.2 삭제하기 
      
      int id = 10;

      String url = "jdbc:oracle:thin:@localhost:1521/xepdb1"; 
      String sql = "DELETE NOTICE WHERE ID = ?";

      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection con = DriverManager.getConnection(url, "","");

      PreparedStatement st = con.prepareStatement(sql); 
      st.setInt(1, id); // index 1부터 시작

      int result = st.executeUpdate(); 

      System.out.println(result); // 1

      st.close();
      con.close();
      
## 6. CRUD를 담당하는 NoticeService 생성

### 6.1 Notice 객체 만들기

독립적인 패키기에 Notice 객체 만들기 

      package com.newlecture.app.entity;

      import java.util.Date;

      public class Notice {

            private int id;        // 노출되지 않도록 private
            private String title;
            private String writerid;
            private Date regDate;
            private String content;
            private int hit;

            public Notice (){

            }

            // Source -> constructor using fields 로 만드는 기능 있음
            public Notice(int id, String title, String writerid, Date regDate, String content, int hit) {

                  this.id = id;
                  this.title = title;
                  this.writerid = writerid;
                  this.regDate = regDate;
                  this.content = content;
                  this.hit = hit;

            }

            // Source -> generate Getters and Setters 로 한 번에 만들거나 만들기
            public int getId() {
                  return id;
            }
            public void setId(int id) {
                  this.id = id;
            }
            public String getTitle() {
                  return title;
            }
            public void setTitle(String title) {
                  this.title = title;
            }
            public String getWriterid() {
                  return writerid;
            }
            public void setWriterid(String writerid) {
                  this.writerid = writerid;
            }
            public Date getRegDate() {
                  return regDate;
            }
            public void setRegDate(Date regDate) {
                  this.regDate = regDate;
            }
            public String getContent() {
                  return content;
            }
            public void setContent(String content) {
                  this.content = content;
            }
            public int getHit() {
                  return hit;
            }
            public void setHit(int hit) {
                  this.hit = hit;
            }
      }
      
### 6.2 NoticeService

(독립적인 패키지에 만들기)

      public class NoticeService {

            // 게시물 리스트
            public List<Notice> getList() throws ClassNotFoundException, SQLException {

                  String url = "jdbc:oracle:thin:@localhost/xepdb1"; 
                  String sql = "SELECT * FROM NOTICE WHERE HIT >= 1";

                  Class.forName("oracle.jdbc.driver.OracleDriver");
                  Connection con = DriverManager.getConnection(url, "ohji","tkwl1414");
                  Statement st = con.createStatement();
                  ResultSet rs = st.executeQuery(sql);

                  List<Notice> list = new ArrayList<Notice>(); // 게시물 객체를 담을 리스트 생성

                  while (rs.next()) { 
                        int id = rs.getInt("id");
                        String title = rs.getString("title");
                        String writerId = rs.getString("writer_id");
                        Date regDate = rs.getDate("regdate");
                        String content = rs.getString("content");
                        int hit = rs.getInt("hit");

                        Notice notice = new Notice( // 게시물 객체 생성
                                          id,
                                          title,
                                          writerId,
                                          regDate,
                                          content,
                                          hit
                                    );

                        list.add(notice); // 리스트에 게시물 객체 추가

                  }

                  rs.close();
                  st.close();
                  con.close();

                  return list;
            }
      }
