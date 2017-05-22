@echo off
title Ardi V1.0
"C:\Program Files\Java\jdk1.8.0_131\bin\java.exe" -Xmx1024m -cp bin;deps/poi.jar;deps/netty.jar;deps/mysql.jar;deps/slf4j.jar;deps/slf4j-nop.jar;deps/jython.jar;log4j-1.2.15.jar; server.Server
pause