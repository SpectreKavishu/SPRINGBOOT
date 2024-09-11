package main

import (
	"context"
	"crypto/tls"
	"fmt"
	"os"
	"sync"

	"github.com/twmb/franz-go/pkg/kgo"
	"github.com/twmb/franz-go/pkg/sasl/scram"
)

func main() {
	topic := "demo-topic"
	hostname, _ := os.Hostname()
	ctx := context.Background()

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

	// Produce 100 messages asynchronously
	var wg sync.WaitGroup
	for i := 1; i < 100; i++ {
		wg.Add(1)
		record := &kgo.Record{
			Topic: topic,
			Key:   []byte(hostname),
			Value: []byte(fmt.Sprintf("Message %d", i)),
		}
		client.Produce(ctx, record, func(record *kgo.Record, err error) {
			defer wg.Done()
			if err != nil {
				fmt.Printf("Error sending message: %v \n", err)
			} else {
				fmt.Printf("Message sent: topic: %s, offset: %d, value: %s \n",
					topic, record.Offset, record.Value)
			}
		})
	}
	wg.Wait()

}
