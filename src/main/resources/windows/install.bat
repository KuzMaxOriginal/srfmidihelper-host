@echo off

java -version 1>nul 2>nul
if ErrorLevel 1 (
   echo.Java is not installed!
) else (
    REG ADD "HKCU\Software\Google\Chrome\NativeMessagingHosts\com.mkuzmin.srfmidihelper" /ve /t REG_SZ /d "%~dp0srfmidihelper.json" /f  >nul 2>nul
    echo.Sight Reading Factory MIDI Helper - Host has been installed.
)

echo.
pause