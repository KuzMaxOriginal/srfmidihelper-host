@echo off

set REG_KEY="HKCU\Software\Google\Chrome\NativeMessagingHosts\com.mkuzmin.srfmidihelper"

reg query %REG_KEY% >nul 2>nul
if not ErrorLevel 1 (
    reg delete %REG_KEY% /f >nul 2>nul
    echo.Sight Reading Factory MIDI Helper - Host has been uninstalled.
) else (
    echo.Sight Reading Factory MIDI Helper - Host hasn't been installed yet.
)

echo.
pause