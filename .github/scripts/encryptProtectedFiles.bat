

@echo off

rem

echo %1

for /r %1 %%f in (*) do echo %%f
