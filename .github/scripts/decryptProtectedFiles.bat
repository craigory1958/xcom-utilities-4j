

@echo off

rem

echo %1

for /r %1 %%f in (*) do call :decrypt "%%f"

goto :eof

:decrypt
echo %1
exit /b

:eof
