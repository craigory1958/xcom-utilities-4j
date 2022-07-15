#!/bin/bash

# Recursively travel a directory and decrypt files

function traverse() {
    for src in "$1"/*
    do
        if [ ! -d "${src}" ] ; then
            dst=${src:22:-4}

            mkdir "$(dirname ${dst})"
            gpg --decrypt --passphrase "$PROTECTED_FILES_PASSPHRASE" --batch -o "${dst}" "${src}"
        else
            traverse "${src}"
        fi
    done
}

function main() {
    traverse "$1"
}

main "$1"
