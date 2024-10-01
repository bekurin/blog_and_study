package main

import (
	"demo-scrapping/config"
	"flag"
	"fmt"
)

var pathFlag = flag.String("config", "./config.toml", "set toml path")

func main() {
	flag.Parse()

	config := config.NewConfig(*pathFlag)
	fmt.Println(config)
}
