
# Korzystając z programu R (https://www.r-project.org/), po zainstalowaniu w nim dodatkowego
# pakietu tmvtnorm, należy wygenerować 1 mln próbek z zawężonego rozkładu i zapisać do pliku.
# library(tmvtnorm)

# Sigma columns 

# first_col <- c(1, 1, 0, 2, -1, -1)
# second_col <- c(1, 16, -6, -6, -2, 12)
# third_col <- c(0, -6, 4, 2, -2, -5)
# fourth_col <- c(2, -6, 2, 25, 0, -17)
# fifth_col <- c(-1, -2, -2, 0, 9, -5)
# sixth_col <- c(-1, 12, -5, -17, -5, 36)

# sigma_matrix <- matrix(c(first_col, second_col, third_col, fourth_col, fifth_col, sixth_col), ncol=6)
# show(sigma_matrix)
# mi_vector <- c(0.003, 0.004, 0.001, 0.002, 0.001, 0.002)

# lower_bound <- rep(-0.1, length = 6)
# upper_bound <- rep(0.1, length = 6)

# 4 degrees of freedom

# numer_of_samples <- 1000000


# result <- rtmvt(n = numer_of_samples, mean = mi_vector, sigma = sigma_matrix, df = 4, upper = upper_bound, lower = lower_bound, algorithm = "gibbs")


# write to the CSV file 
# write.table(result, 'samples.csv', sep=',')



# Statystyki 
# Uwzględniając wszystkie próbki, dla poszczególnych aktywów i portfela 
# należy wyznaczyć wartości następujących statystyk:

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

ten_percent_means <- list()
for(i in 1:6) {
  boolean_arr <- df[,i] > column_quantiles[[i]]
  column_arr <- df[,i]
  intermediate_mean <- mean(column_arr[boolean_arr])
  
  ten_percent_means <- c(ten_percent_means, intermediate_mean)
}

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
