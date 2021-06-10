# odczytaj plik csv
df <- read.csv('/Users/tomek/Desktop/psd-21l/samples.csv', header = FALSE)
df[[1]] <- NULL # delete first column as it contains indexes

#########
# Średnia dla aktywów
#########
column_means <- list()
## typeof(column_means)
for(i in 1:6) { 
  col_mean <- mean(df[[i]])
  column_means <- c(column_means, col_mean)
}
show(column_means)

#########
# Mediana 
#########
column_medians <- list()
## typeof(column_means)
for(i in 1:6) { 
  col_median <- median(df[[i]])
  column_medians <- c(column_medians, col_median)
}
show(column_medians)

# kwantyl rzędu 0.1 
column_quantiles <- list() 
for(i in 1:6) { 
  intermediate_quantile <- quantile(df[[i]], probs = c(0.1))
  column_quantiles <- c(column_quantiles, intermediate_quantile)
}

show(column_quantiles)

# Średnia z 10% najmniejszych stóp zwrotu 

# ten_percent_means <- list()
# for(i in 1:6) {
#   boolean_arr <- df[,i] > column_quantiles[[i]]
#   column_arr <- df[,i]
#   intermediate_mean <- mean(column_arr[boolean_arr])
  
#   ten_percent_means <- c(ten_percent_means, intermediate_mean)
# }

# sposob dwa
ten_percentage_means_df <- list()
for(i in 1:6) {
  current_column <- df[[i]]
  current_column <- current_column[order(current_column)]
  length_idx <- 100 * 0.1 # TODO: tutaj milion
  n_first <- current_column[1:length_idx]
  show(n_first)
  intermediate <- mean(n_first)
  ten_percentage_means_df <- c(ten_percentage_means_df, intermediate)
}

show(ten_percentage_means_df)


show(ten_percent_means)

# miara bezpieczeństwa oparta na odchyleniu przeciętnym: µ −
# 1 / 2T PT t=1 |µ − rt|, 
# gdzie µ –średnia, 
# rt – stopa zwrotu dla próbki 
# t, T – liczba wszystkich próbek,

std_deviations <- list()
for(i in 1:6) { 
  intermediate_value <- 1/(2 * nrow(df)) * sum(abs(column_means[[i]] - df[[i]]))
  std_deviations <- c(std_deviations, intermediate_value)
}
show(std_deviations)

#  miara bezpieczeństwa oparta na średniej różnicy Giniego: µ −
# 1 2T2 / PT t 0=1 PT t 00=1 |rt 0 − rt 00|, oznaczenia jw.
library(Hmisc)
safety_measure_gini <- list()

for(i in 1:6) { 
  intermediate_col_gini <- GiniMd(df[[i]])
  safety_measure_gini <- c(safety_measure_gini, intermediate_col_gini)
}

show(safety_measure_gini)


# Wszystkie statystyki razem
# Mediana
show(column_medians)
# średnia
show(column_means)
# kwantyl rzedu 0.1
show(column_quantiles)
# średnia z 10% najmniejszych stóp zwrotu,
show(ten_percent_means)
# miara bezpieczeństwa oparta na odchyleniu przeciętnym
show(std_deviations)
# miara bezpieczeństwa oparta na średniej różnicy Giniego
show(safety_measure_gini)
