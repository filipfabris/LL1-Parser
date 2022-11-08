#! /bin/bash

for dire in ./test/*
do
  difs="$(java -cp 'C:\Users\filip\Desktop\Java_e\PPJ2-proba\out\production\PPJ2-proba'  ParserMain < $dire/test.in  | diff $dire/test.out -)"
  java -classpath 'C:\Users\filip\Desktop\Java_e\PPJ2-proba\out\production\PPJ2-proba'  ParserMain < $dire/test.in > text.txt
	if [ "$difs" = "" ];
	then
		echo "$dire : [OK]"
	else
		echo "$dire : "
    echo "$difs"
	fi

done