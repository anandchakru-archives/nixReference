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
changeOrAppend() {
  grep -q '^'$2 $1 && sed -i 's/^'$2'.*/'$2'='$3'/' $1 || echo $2'='$3 >> $1
}
replaceOrAppend() {
  if grep -q "^$2" $1
  then
      sed -i "s/^$2.*$/$3/" $1
  else
      echo "$3" >>$1
  fi
}
#replaceOrAppend "/etc/systemd/logind.conf" "#HandleLidSwitch=suspend" "HandleLidSwitch=ignore"
changeOrAppend "/etc/systemd/logind.conf" "HandleLidSwitch" "ignore"
systemctl restart systemd-logind
```
List app using a port(eg 4444) and kill it
```
lsof -i -n -P | grep 4444
    java      9945 chakru   15u  IPv6  58576      0t0  TCP *:4444 (LISTEN)
kill -9 9945
```

List all users:`awk -F: '{ print $1 }' /etc/passwd`

Disk Space Human Readable: `df -kh`

Find file without permission denied: `find /. -name 'fname' 2>&1 | grep -v 'Permission denied'`

List only directories: `ls -l| grep '^d'`

List jars loaded by java (with pid 21248): `/usr/sbin/lsof -p 21284 | grep jar > /tmp/j.txt`

Memory details: `free -m`

Highlight with tail: `tail -f file.log | sed "s/\\(TESTTOHIGHLIGHT\\)/{%CTRL%}v{%CTRL%}[[46;1m\\1{%CTRL%}v{%CTRL%}[[0m/g"`

Progress/Status of dd: `watch -n5 'sudo kill -USR1 $(pgrep ^dd)'`

Date/Time:
```
#!/bin/sh
d=$(date +"%Y%m%d%H%M%S")
echo datetime: $d
```

##CPU

Generate load: `yes > /dev/null &`

Stop load: `killall yes`

Monitor cpu load: `top`

##File permissions
- `---` `0`
- `--x` `1`
- `-w-` `2`
- `-wx` `3`
- `r--` `4`
- `r-x` `5`
- `rw-` `6`
- `rwx` `7`
```
$$$: ls -l
drwxrw-r-x  2 chakru group1 4096 Jun  1 22:56 nixReference
```
Directory flag - `d`

Owner(**chakru**)'s access - `rwx`

**group1** user's access - `rw-`

Every other user's access - `r-x`

```
$$$: ls -l /usr/bin/passwd
-rwsr-sr-x 1 root root 54256 Mar 29 02:25 /usr/bin/passwd
```
s instead of x means, SUID/SGID bit is set. Which means, when a normal user executes passwd, he gains root access only for passwd does (eg: update password into /etc/shadow, for which only root has write access)

S instead of x means, the file doesnt have x (execute permission) but only SUID/SGID is set

List installed items:
`dpkg --list`

Uninstall item:
`sudo apt-get --purge remove unetbootin`

Commands:
```
$$$: ls -l
drwxrwxr-x  2 chakru group1 4096 Jun  1 22:56 nixReference
$$$: stat -c "%a %n" nixReference
775 nixReference

$$$: chmod -R 777 nixReference

$$$: stat -c "%a" nixReference
777

$$$: ls -l
drwxrwxrwx  2 chakru group1 4096 Jun  1 22:56 nixReference

$$$: chgrp -R smbshare nixReference

$$$: ls -l
drwxrwxrwx 2 chakru smbshare 4096 Jun  1 22:56 nixReference

$$$: sudo chown -R smbchakru nixReference

$$$: ls -l
drwxrwxrwx 2 smbchakru smbshare 4096 Jun  1 22:56 nixReference

$$$: chmod -R ug+s nixReference

$$$: ls -l
drwsrwsrwx 2 smbchakru smbshare 4096 Jun  1 22:56 nixReference

$$$: chmod -R -x nixReference

$$$: ls -l
drwSrwSrwx 2 smbchakru smbshare 4096 Jun  1 22:56 nixReference

```
##Disk utils
Backup: `dd if=/dev/sdaX | bzip2 -9f >/media/chakru/90b/bckp.img.bz2`

Restore: `bunzip2 -dc /media/chakru/90b/bckp.img.bz2 | dd of=/dev/sdaX`

Mount iso file: `mount -t iso9660 -o loop win7.iso /media/chakru/90b`

Mount CD: `mount -t iso9660 -o ro /dev/cdrom /media/chakru/90b`

Unmount forcefully: `umount -f /media/chakru/90b`

Find drives UUID: `blkid`

List all mounts: `mount`

Unmount all: `umount -a`

Format drive with fat: `sudo mkfs.vfat /dev/sdaX`

Format drive with ntfs: `sudo mkfs.ntfs /dev/sdaX`

Format drive with ext4: `sudo mkfs.ext4 /dev/sdaX`

Partition disk: `fdisk /dev/sda`
