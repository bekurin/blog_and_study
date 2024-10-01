package network

import (
	"demo-scrapping/authenticator"
	"demo-scrapping/config"
	"demo-scrapping/service"

	"github.com/gin-gonic/gin"
)

type Network struct {
	config        *config.Config
	engine        *gin.Engine
	service       service.ServiceImpl
	authenticator authenticator.AuthenticatorImpl
}

func NewNetwork(
	config *config.Config,
	service service.ServiceImpl,
	authenticator authenticator.AuthenticatorImpl,
) *Network {
	network := &Network{
		config:        config,
		service:       service,
		authenticator: authenticator,
		engine:        gin.New(),
	}
	return network
}

func (network *Network) Run() error {
	return network.engine.Run(network.config.Network.Port)
}
