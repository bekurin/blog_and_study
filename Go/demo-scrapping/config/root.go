package config

import (
	"os"

	"github.com/naoina/toml"
)

type Config struct {
	Network struct {
		Port string
	}

	Authenticator struct {
		Secret   string
		Account  string
		Issuer   string
		FileName string
	}

	Database struct {
		Database string
		Url      string
	}
}

func NewConfig(path string) *Config {
	config := new(Config)

	if file, error := os.Open(path); error != nil {
		panic(error)
	} else if error = toml.NewDecoder(file).Decode(config); error != nil {
		panic(error)
	}

	return config
}
