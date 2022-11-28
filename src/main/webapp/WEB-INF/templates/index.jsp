<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Aplikacja Sklep Spring</title>
<link rel="stylesheet" type="text/css" href="styl.css">
</head>
<body>
<h1>Spis treści</h1>
<h2>Podstawy</h2>
<ul>
<li><a href="/">Spis treści</a></li>
<li><a href="/hello">Hello world</a></li>
<li><a href="/time">Która godzina</a></li>
</ul>

<h2>Logowanie</h2>
<ul>
<li><a href="/login">zaloguj się</a>
<li><a href="/logout">wyloguj się</a>
<li><a href="/whoami">sprawdź kim jesteś</a>
</ul>


<h2>Katalog produktów</h2>
<ul>
<li><a href="/products">products</a> - wszystkie produkty</li>
<li><a href="/products/1">products/1</a> - jeden produkt</li>
<li><a href="/products/9">products/9</a> - nieistniejący produkt</li>
<li><a href="/products/szukaj">wyszukiwarka</a></li>
<li><a href="/products/szukaj?name=pralka">wyszukiwarka/pralka</a></li>
<li><a href="/products/new">nowy produkt</a></li>
<li><a href="/products/1/edit">edycja produktu</a></li>
</ul>

<h2>Edycja klienta</h2>
<ul>
<li><a href="/customers">lista klientów</a>
<li><a href="/customers/new">nowy klient</a>
<li><a href="/customers/ala@example.com/edit">edycja klienta</a>
</ul>

</body>
</html>
