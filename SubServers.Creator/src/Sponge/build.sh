# SubCreator SpongeVanilla Build Script
#
#!/usr/bin/env bash
if [[ -z "$sp_version" ]]
  then
    echo ERROR: No Build Version Supplied
    rm -Rf "$0"
    exit 1
fi
function __DL() {
    if [[ -x "$(command -v wget)" ]]; then
        wget -O "$1" "$2"; return $?
    else
        curl -o "$1" "$2"; return $?
    fi
}
echo Downloading SpongeVanilla...
if [[ -f "Sponge.jar" ]]; then
    if [[ -f "Sponge.old.jar.x" ]]; then
        rm -Rf Sponge.old.jar.x
    fi
    mv Sponge.jar Sponge.old.jar.x
fi
__DL Sponge.jar "https://repo.spongepowered.org/maven/org/spongepowered/spongevanilla/$sp_version/spongevanilla-$sp_version.jar"; __RETURN=$?
if [[ $__RETURN -eq 0 ]]; then
    echo Cleaning Up...
    rm -Rf "$0"
    exit 0
else
    echo ERROR: Failed downloading Sponge. Is MinecraftForge.net down?
    if [[ -f "Sponge.old.jar.x" ]]; then
        if [[ -f "Sponge.jar" ]]; then
            rm -Rf Sponge.jar
        fi
        mv Sponge.old.jar.x Sponge.jar
    fi
    rm -Rf "$0"
    exit 3
fi
exit 2