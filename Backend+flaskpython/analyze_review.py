from textblob import TextBlob

# Ask user to enter a review
review = input("Enter your review: ")

# Create a TextBlob object
blob = TextBlob(review)

# Get the sentiment
sentiment = blob.sentiment

# Display results
print("Sentiment Polarity:", sentiment.polarity)
print("Sentiment Subjectivity:", sentiment.subjectivity)
