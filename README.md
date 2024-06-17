<img src="https://capsule-render.vercel.app/api?type=wave&color=auto&height=200&section=header&width=1000&text=Board%20&fontSize=90" />
<div>  
  <h3>개발 목적</h3>
  CRUD 흐름 복습 및 이미지 업로드, 추후 기능 업데이트를 할 V2를 위한 작업
  <h3>사용 언어</h3>
  <img src="https://img.shields.io/badge/Java-FF7800?style=flat&logo=Java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Javascript-F7DF1E?style=flat&logo=Javascript&logoColor=white"/>
  <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=HTML-5&logoColor=white"/>
  
  <h3>사용 툴</h3>
  <img src="https://img.shields.io/badge/intellijidea-000000?style=flat&logo=intellijidea&logoColor=white"/>
  <img src="https://img.shields.io/badge/visualstudiocode-007ACC?style=flat&logo=visualstudiocode&logoColor=white"/>
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=flat&logo=springboot&logoColor=white"/>
  
  <h3>메인 개발 환경</h3>
  <img src="https://img.shields.io/badge/intellijidea-000000?style=flat&logo=intellijidea&logoColor=white"/>
  
  <h3>부트 의존성</h3>
  <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=flat&logo=thymeleaf&logoColor=white"/>
  <img src="https://img.shields.io/badge/jpa-83B81A?style=flat&logo=jpa&logoColor=white"/>
  <img src="https://img.shields.io/badge/web-0085CA?style=flat&logo=web&logoColor=white"/>
  <img src="https://img.shields.io/badge/lombok-FF9A00?style=flat&logo=lombok&logoColor=white"/>
  <img src="https://img.shields.io/badge/h2-000000?style=flat&logo=h2&logoColor=white"/>
  <img src="https://img.shields.io/badge/validation-EF2D5E?style=flat&logo=validation&logoColor=white"/>
  <img src="https://img.shields.io/badge/devtools-0099E5?style=flat&logo=devtools&logoColor=white"/>
  <img src="https://img.shields.io/badge/mysql-4479A1?style=flat&logo=mysql&logoColor=white"/>

  <h3>ERD</h3>
  추후 업데이트
  <hr>
</div>
<div>

<h3>기능</h3>
<ul>
<li>회원 가입, 로그인</li>
<li>게시글 작성(이미지 업로드), 수정(이미지 수정X), 삭제</li>
<li>댓글 작성, 삭제(권한 작성자, 게시글 주인)</li>
<li>게시글, 댓글 페이징</li>
</ul>
    <hr>

<h3>이슈</h3>
<ul>
<li>이미지 업로드한 게시판 작성시 서버 재부팅 전까지 내부에 저장된 이미지 불러오기 실패</li>
</ul>
<hr>

<h3>추후 업데이트, 수정 계획</h3>
<ul>
<li>JPA가 아닌 jdbc로 이용</li>
<li>mysql 프로시저로 CRUD작동으로 변경 계획</li>
<li>이미지 업로드 실시간 반영</li>
<li>익셉션 핸들러 이용(예외 처리 유지보수 위함)</li>
</ul>

<pre>
*주의사항*
yml 본인 mysql 계정정보 등록 필요
yml update로 변경시 InitDb 클래스파일 주석처리 필요
이미지 파일 저장 경로 프로젝트 폴더 위치까지 수정 필요
</pre>
</div>
