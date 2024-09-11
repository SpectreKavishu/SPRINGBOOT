package main

import (
	"context"
	"crypto/tls"
	"fmt"
	"github.com/twmb/franz-go/pkg/kadm"
	"github.com/twmb/franz-go/pkg/kgo"
	"github.com/twmb/franz-go/pkg/sasl/scram"
)

func main() {
	topic := "demo-topic"
	seeds := []string{ "creppfp2b32l8feglrkg.any.eu-central-1.mpx.prd.cloud.redpanda.com:9092" }
	opts := []kgo.Opt{}
	opts = append(opts,
		kgo.SeedBrokers(seeds...),
	)

	// Initialize public CAs for TLS
	opts = append(opts, kgo.DialTLSConfig(new(tls.Config)))

    // Initializes SASL/SCRAM
	opts = append(opts, kgo.SASL(scram.Auth{
		User: "goyalk",
		Pass: "pass",
	}.AsSha256Mechanism()))

	client, err := kgo.NewClient(opts...)
	if err != nil {
		panic(err)
	}
	defer client.Close()

	admin := kadm.NewClient(client)
	defer admin.Close()

	ctx := context.Background()
	// Create a topic with a single partition and cluster-default replicas
	resp, err := admin.CreateTopics(ctx, 1, -1, nil, topic)
	if err != nil {
		panic(err)
	}

	for _, ctr := range resp {
		if ctr.Err != nil {
			fmt.Printf("Unable to create topic '%s': %s\n", ctr.Topic, ctr.Err)
		} else {
			fmt.Printf("Created topic '%s'\n", ctr.Topic)
		}
	}
}
