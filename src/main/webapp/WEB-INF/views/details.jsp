<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h2 class="text-center text-info">Spring Boot Social Login Example</h2><hr/>
        <ul>
        	<li>Name= ${user.getName()}</li>
        	<li>Email= ${user.getEmail()}</li>
        	<li>Birthday= ${user.getBirthday()}</li>
        	<li>Gender= ${user.getGender()}</li>
<%--         	<li>HomeTown= ${user}</li> --%>
<%--         	<li>Location= ${user.getLocation().getName()}</li> --%>
        </ul>
    </div>
</body>
</html>