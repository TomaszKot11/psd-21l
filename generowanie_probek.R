
# Korzystając z programu R (https://www.r-project.org/), po zainstalowaniu w nim dodatkowego
# pakietu tmvtnorm, należy wygenerować 1 mln próbek z zawężonego rozkładu i zapisać do pliku.
# library(tmvtnorm)

# Sigma columns 

first_col <- c(1, 1, 0, 2, -1, -1)
second_col <- c(1, 16, -6, -6, -2, 12)
third_col <- c(0, -6, 4, 2, -2, -5)
fourth_col <- c(2, -6, 2, 25, 0, -17)
fifth_col <- c(-1, -2, -2, 0, 9, -5)
sixth_col <- c(-1, 12, -5, -17, -5, 36)

sigma_matrix <- matrix(c(first_col, second_col, third_col, fourth_col, fifth_col, sixth_col), ncol=6)
show(sigma_matrix)
mi_vector <- c(0.003, 0.004, 0.001, 0.002, 0.001, 0.002)

lower_bound <- rep(-0.1, length = 6)
upper_bound <- rep(0.1, length = 6)

# 4 degrees of freedom

numer_of_samples <- 1000000


result <- rtmvt(n = numer_of_samples, mean = mi_vector, sigma = sigma_matrix, df = 4, upper = upper_bound, lower = lower_bound, algorithm = "gibbs")


# write to the CSV file 
write.table(result, 'samples.csv', sep=',')

