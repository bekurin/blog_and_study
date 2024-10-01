package app

import (
	"demo-scrapping/authenticator"
	"demo-scrapping/config"
	"demo-scrapping/network"
	"demo-scrapping/repository"
	"demo-scrapping/service"
)

type App struct {
	config  *config.Config
	network *network.Network

	authenticator authenticator.AuthenticatorImpl
	repository    repository.RepositoryImpl
	service       service.ServiceImpl
}

func NewApp(config *config.Config) *App {
	app := &App{config: config}
	return app
}
