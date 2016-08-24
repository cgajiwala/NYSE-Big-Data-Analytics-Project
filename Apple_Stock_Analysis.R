

apple_dataset <- read.csv("Apple_NYSE_NN.csv")


apple_stock_data <- cbind(apple_dataset$Date,apple_dataset$Open, apple_dataset$High, apple_dataset$Low, apple_dataset$Close, apple_dataset$Volume, apple_dataset$Adj.Close)


apple_stock_data.lm <- lm(Adj.Close~ Open + High + Low + Volume+ Close, data = apple_stock_data)


apple_stock_data.lm



summary(apple_stock_data.lm)

predict(apple_stock_data.lm)


predict(apple_stock_data.lm, level = 0.95)





qqnorm(apple_stock_data.lm)




#Equation

#Adj_close=112.6655044190974-0.0310677272107open+2.8484184978663high-2.5652874460933low-0.3175670409389close-0.0000003572271volume

# Analysis:

abline(apple_stock_data.lm)

qqnorm(apple_stock_data.lm)

predict(apple_stock_data.lm)


confint(apple_stock_data.lm)


apple_stock_data.res=resid(apple_stock_data.lm)


apple_stock_data.res

plot(apple_stock_data.lm)

predict(apple_stock_data.lm, level = 0.95)



####Predict the value means it gives the range of values the actual values got deviated from theoretical.
apple_stdres


qqnorm(apple_stdres,ylab = "Standardized Residuals", xlab = "Normal Scores", main = "apple stock standard residuals")

#Equation is ___

summary(apple_stock_data)






mean_aclose= mean(apple_stock_data$Adj.Close)


mean_aclose

abline(apple_stock_data.lm, col = "red")

plot(apple_stock_data.lm)


termplot(apple_stock_data.lm)

summary(apple_stock_data.lm)


apple_stock_data.lm

#gives the below info:

#Coefficients:
 # (Intercept)         Open         High          Low        Close       Volume  
   #1.127e+02   -3.107e-02    2.848e+00   -2.565e+00   -3.176e-01   -3.572e-07

#open, high, low, close, volume are the slopes.

#correlation to other stocks 

#RNN algo

#Dow Jones

#some variable.names to use
