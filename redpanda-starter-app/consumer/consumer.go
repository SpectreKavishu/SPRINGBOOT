package main

import (
	"context"
	"crypto/tls"
	"fmt"

	"github.com/twmb/franz-go/pkg/kgo"
	"github.com/twmb/franz-go/pkg/sasl/scram"
)

func main() {
	topic := "demo-topic"
	ctx := context.Background()

	seeds := []string{ "creppfp2b32l8feglrkg.any.eu-central-1.mpx.prd.cloud.redpanda.com:9092" }
	opts := []kgo.Opt{}
	opts = append(opts,
		kgo.SeedBrokers(seeds...),
		kgo.ConsumeTopics(topic),
		kgo.ConsumeResetOffset(kgo.NewOffset().AtStart()),
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

	for {
		fetches := client.PollFetches(ctx)
		if errs := fetches.Errors(); len(errs) > 0 {
			// All errors are retried internally when fetching, but non-retriable
			// errors are returned from polls so that users can notice and take
			// action.
			panic(fmt.Sprint(errs))
		}
		iter := fetches.RecordIter()
		for !iter.Done() {
			record := iter.Next()
			topicInfo := fmt.Sprintf("topic: %s (%d|%d)",
				record.Topic, record.Partition, record.Offset)
			messageInfo := fmt.Sprintf("key: %s, Value: %s",
				record.Key, record.Value)
			fmt.Printf("Message consumed: %s, %s \n", topicInfo, messageInfo)
		}
	}
}
