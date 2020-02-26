<<<<<<< HEAD
<#import "/spring.ftl" as spring />

=======
>>>>>>> 861fb26b69bacd0683aa77f740230b6589c02fa4
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
<<<<<<< HEAD
    <title>Activate account with Spring Boot, MongoDB, NGINX, Docker Compose</title>
</head>
<body>
<h2>Verify your email</h2>

<@spring.bind "verificationForm"/>
<#if verificationForm?? && noErrors??>
    Sent a confirmation link to your inbox ${verificationForm.email}<br>
<#else>
    <form action="/email-verification" method="post">
        Email:<br>
        <@spring.formInput "verificationForm.email"/>
        <@spring.showErrors "<br>"/>
        <br><br>
        <input type="submit" value="Submit">
    </form>
</#if>
=======
    <title>$Title$</title>
</head>
<body>
$END$
>>>>>>> 861fb26b69bacd0683aa77f740230b6589c02fa4
</body>
</html>