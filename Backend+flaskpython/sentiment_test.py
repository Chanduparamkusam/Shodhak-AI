from textblob import TextBlob

# Sample review
review = "This product is excellent! I highly recommend it to everyone."

# Create TextBlob object
blob = TextBlob(review)

# Get sentiment
sentiment = blob.sentiment

print("Review:", review)
print("Sentiment Polarity:", sentiment.polarity)
print("Sentiment Subjectivity:", sentiment.subjectivity)
