<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Która godzina (JSP)</title>
</head>
<body>
<h1>Która godzina (JSP)</h1>
<p>Teraz jest godzina <strong>${dt}</strong></p>

<h2>Wybrane pola:</h2>
<ul>
<li>rok: ${dt.year}</li>
<li>dzień roku: ${dt.dayOfYear}</li>
</ul>

</body>
</html>
