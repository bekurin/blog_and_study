package network

import (
	"strings"

	"github.com/gin-gonic/gin"
)

type API_REQUEST_METHOD uint8

const (
	GET API_REQUEST_METHOD = iota
	POST
	PUT
	PATCH
	UPDATE
	DELETE
)

type header struct {
	Result int    `json:"status"`
	Data   string `json:"data"`
}

type response struct {
	*header
	Result interface{} `json:"result"`
}

func res(context *gin.Context, code int, res interface{}, data ...string) {
	context.JSON(code, &response{
		header: &header{
			Result: code,
			Data:   strings.Join(data, " ,"),
		},
		Result: res,
	})
}

func (network *Network) register(path string, method API_REQUEST_METHOD, handlerFunc ...gin.HandlerFunc) gin.IRoutes {
	switch method {
	case GET:
		return network.engine.GET(path, handlerFunc...)
	case POST:
		return network.engine.POST(path, handlerFunc...)
	case PUT:
		return network.engine.PUT(path, handlerFunc...)
	case PATCH:
		return network.engine.PATCH(path, handlerFunc...)
	case DELETE:
		return network.engine.DELETE(path, handlerFunc...)
	default:
		return nil
	}
}
