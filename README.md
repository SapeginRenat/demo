## DEMO SERVICE 

##### RESTful spring boot 

example api: 

`Auto generate 5 user with default params`

```
Get Method
/api/personage/{id}

{
    "id": 2,
    "exp": 0,
    "level": 1
}
```

```
@Get Method 
/personage/{id}/add-exp?exp=222

{
    "id": 2,
    "exp": 122,
    "level": 50
}
```

```aidl
@Post Method

/personage/create

body:

{ "id" : 7}
```

Levels should be in `level.properties` or auto generate if empty

```
level.init.number-map={\
  1:100, \
  10:110, \
  20:120, \
  30:150, \
  40:190,  \
  50:240,  \
  60:300,  \
  70:370,  \
  80:450,  \
  90:540,  \
  100:540  \ note last elements value should equals previos value(need fix)
  }
```
default:
```$xslt
{1:100, 10:110, 20:120, 30:150, 40:190, 50:200}
```

Run with docker-compose(ubuntu*):

```aidl

cd root project

ls 
docker-compose.yml  Dockerfile  pom.xml  README.md  src

//optionality
sudo apt install maven -y

mvn package 

// run
sudo docker-compose up -d 

// stop
sudo docker-compose down


```

or run jar file!



