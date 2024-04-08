#!/bin/bash

# 루트 경로가 존재하는지 확인
if [ -d "/var/lib/jenkins/workspace" ]; then
    # 루트 경로에 있는 폴더 삭제
    rm -rf "/var/lib/jenkins/workspace"/*
    echo "루트에 있는 폴더를 삭제했습니다."
else
    echo "루트에 해당하는 경로가 존재하지 않습니다."
fi
