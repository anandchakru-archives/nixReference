# nixReference
SSH Connect: `ssh chakru@192.168.1.1`

SSH Host key verification failed: `ssh-keygen -R 192.168.1.1`

Find logged in User:
```
#!/bin/sh
lgduser=$(logname)
echo $lgduser
```

Exit if not root (or sudo):

```
#!/bin/sh
if (( $EUID != 0 )); then
    echo "Please run as root"
    exit
fi
```

Append to a file using tee:
```
#!/bin/sh
echo -e "\n[autoexec]\nmount c /c\nc:\ndir\n" | tee -a ~/.dosbox/dosbox-0.74.conf
```

Systemctl (services):
```
systemctl start mongodb
systemctl status mongodb
systemctl enable mongodb
```

Create Desktop Shotcut
```
cp STS.desktop /usr/share/applications/STS.desktop
chmod +x /usr/share/applications/STS.desktop
```
Content of STS.desktop is
```
[Desktop Entry]
Type=Application
Terminal=false
Name=STS
Icon=/opt/apps/sts-bundle/sts-3.7.3.RELEASE/icon.xpm
Exec=/opt/apps/sts-bundle/sts-3.7.3.RELEASE/STS
Categories=Utility;
```

Do nothing on laptop lid close
```
#!/bin/sh
replaceOrAppend() {
  if grep -q "^$2" $1
  then
      sed -i "s/^$2.*$/$3/" $1
  else
      echo "$3" >>$1
  fi
}
replaceOrAppend /etc/systemd/logind.conf "#HandleLidSwitch=suspend" "HandleLidSwitch=ignore"
```
