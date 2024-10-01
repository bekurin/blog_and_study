package main

import (
	"flag"
	"fmt"
)

var pathFlag = flag.String("config", "./config.toml", "set toml path")

func main() {
	flag.Parse()

	fmt.Println(*pathFlag)
	fmt.Println("hello world")
}
