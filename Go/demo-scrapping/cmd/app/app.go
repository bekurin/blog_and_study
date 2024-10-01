package app

import (
	"demo-scrapping/authenticator"
	"demo-scrapping/config"
	"demo-scrapping/network"
	"demo-scrapping/repository"
	"demo-scrapping/service"
	"os"
	"os/signal"
	"syscall"
)

type App struct {
	config  *config.Config
	network *network.Network

	authenticator authenticator.AuthenticatorImpl
	repository    repository.RepositoryImpl
	service       service.ServiceImpl

	stop chan struct{}
}

func NewApp(config *config.Config) *App {
	app := &App{
		config: config,
		stop:   make(chan struct{}),
	}

	app.network = network.NewNetwork(config, app.service, app.authenticator)
	channel := make(chan os.Signal, 1)
	signal.Notify(channel, syscall.SIGINT)

	go func() {
		<-channel
		app.Exit()
	}()

	return app
}

func (app *App) Wait() {
	<-app.stop
	os.Exit(1)
}

func (app *App) Exit() {
	app.stop <- struct{}{}
}

func (app *App) Run() {
	app.network.Run()
}
