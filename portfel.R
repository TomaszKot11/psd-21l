df <- read.csv('C:\\Users\\kotto\\OneDrive\\Pulpit\\psd_koniec\\psd-21l\\samples.csv', header = FALSE)
show(nrow(df))
df[[1]] <- NULL # delete first column as it contains indexes
show(df)


weights <- c(0.2, 0.2, 0.2, 0.15, 0.15, 0.1)
portfolio <- rep(0, 1000000)

for (i in 1:1000000) {
  computed_value <- df[i,1] * weights[1] + df[i,2] * weights[2] +df[i,3] * weights[3] + df[i,4] * weights[4] + df[i,5] * weights[5] + df[i,6] * weights[6]
  portfolio[i] <- computed_value
}

write.table(portfolio, 'portfel.csv', sep=',')